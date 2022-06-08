import logging as log
import os
import tarfile
from datetime import datetime

from gcloud import storage
from oauth2client.service_account import ServiceAccountCredentials


def create_backup_as_tarfile(dump_filename: str, tar_filename: str) -> str:
    """
    Return a tar compressed backup of the database created via pg_dump.
    :param dump_filename: name of the sql dump file to create
    :param tar_filename: name of the tar file to create
    :return: name of the tar file created
    """
    command = f'pg_dump --dbname=postgresql://{DB_USERNAME}:{DB_PASSWORD}@{DB_HOST}:{DB_PORT}/{DB_NAME} > {dump_filename}'
    try:
        with tarfile.open(tar_filename, "w:gz") as tar:
            try:
                log.info(f'Executing "{command}"')
                os.system(command)
                log.info(f'Database dump {dump_filename} successfully created.')
            except Exception as ex:
                log.error(f'Error while dumping database. Message: {ex}')
            tar.add(dump_filename)
            tar.close()
        log.info(f'Dump file {dump_filename} successfully compressed to {tar_filename}.')
        os.remove(dump_filename)
        log.info(f'Database dump {dump_filename} successfully deleted.')
        return tar_filename
    except Exception as ex:
        log.error(f'Error while trying to generate {tar_filename}. Error {ex}')


def upload_to_gcp_bucket(filename_to_upload: str, credentials: str) -> None:
    client = storage.Client(credentials=credentials)
    filename_to_upload = filename_to_upload
    try:
        bucket = client.get_bucket(BUCKET_NAME)
        blob = bucket.blob(filename_to_upload)
        blob.upload_from_filename(filename_to_upload)
        log.info(f'File {filename_to_upload} successfully uploaded to {BUCKET_NAME}.')
    except Exception:
        log.error(f'Error while trying to update {filename_to_upload} to {BUCKET_NAME}.')
    return


log.basicConfig(level=log.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

log.info('Hi from python backuper!')

try:
    CREDENTIALS = {
        'type': 'service_account',
        'client_id': os.environ['client_id'],
        'client_email': os.environ['client_email'],
        'private_key_id': os.environ['private_key_id'],
        'private_key': os.environ['private_key'],
    }
    BUCKET_NAME = os.environ['bucket_name']
    DB_NAME = os.environ['db_name']
    DB_USERNAME = os.environ['db_username']
    DB_PASSWORD = os.environ['db_password']
    DB_HOST = os.environ['db_host']
    DB_PORT = os.environ['db_port']
    log.info(f'Environment variables successfully loaded.')
except Exception as ex:
    log.error(f'Error while reading from environment variables. Message: {ex}')

dump_filename = 'dump.sql'
tar_filename = f'sip-{datetime.now().strftime("%m_%d_%Y-%H_%M_%S")}-dump.tar'
tarfile_name: str = create_backup_as_tarfile(dump_filename, tar_filename)
upload_to_gcp_bucket(tarfile_name, ServiceAccountCredentials.from_json_keyfile_dict(CREDENTIALS))
