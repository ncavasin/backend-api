# cat backuppod.py
# !/usr/bin/python3
import logging
import os.path
import tarfile
# import json
# import schedule
import time


def removeOldFiles(days_retention, base_path):
    time_in_secs = time.time() - (days_retention * 24 * 60 * 60)
    for root, dirs, files in os.walk(base_path, topdown=False):
        for file in files:
            full_path = os.path.join(root, file)
            stat = os.stat(full_path)
            if stat.st_mtime <= time_in_secs:
                # logging.info("removing file {}".format(full_path))
                logging.info("removing file {}".format(full_path))
                os.remove(full_path)
                logging.info("file {} has been removed ".format(full_path))


def make_tarfile(output_filename, source_dirs):
    with tarfile.open(output_filename, "w:gz") as tar:
        splitted_dirs = source_dirs.split(',')
        for dir in splitted_dirs:
            # logging.info(os.path.basename(dir))
            # logging.info(dir)
            tar.add("data/" + dir, arcname=os.path.basename(dir))
    tar.close

    [{
        "service": "gitlab",
        "folders": "gitlab-etc,gitlab-opt,gitlab-log"
    }, {
        "service": "jenkins",
        "folders": "jenkins-data"
    }, {
        "service": "jfrog",
        "folders": "jfrog"
    }, {
        "service": "sonar",
        "folders": "sonar-data,sonar-extension"
    }]

}

now = datetime.now()
dt_string = now.strftime("%d-%m-%Y-%H:%M:%S")
# Initialize threads list to join
# threads=[]
# for majorkey, subdict in data.items():
# thread.removeOldFiles()
# th = threading.Thread(target=removeOldFiles(1,"data/backups/frompods", ))
# th.start()

for i in subdict:
    removeOldFiles(3, "data/backups/frompods/{}".format(i['service']))
    msg = "data/backups/frompods/{}/{}-{}.tar.gz".format(i['service'], i['service'], dt_string)
    logging.info("Starting with" + msg)
    make_tarfile(msg, i['folders'])
#     thread=TarFiles(msg, i['folders'])
#     threads.append(thread)
#     thread.start()

# for threadSon in threads:
#     threadSon.join()
#     logging.info("join Thread ")

# main_method()

# schedule.every(1).day.at("23:30").do(main_method)
# #schedule.every(10).minutes.do(main_method)
# # schedule.every(1).seconds.do(main_method)
# while True:
#     try:
#         schedule.run_pending()
#         time.sleep(60)
#     except (KeyboardInterrupt, SystemExit):
#         exit(1)
