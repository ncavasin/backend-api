FROM python:3.9.13-alpine3.16
RUN pip install gcloud
RUN apk add --no-cache postgresql-client
RUN mkdir -p /workspace
WORKDIR /workspace
COPY backuper.py /workspace
COPY myfile.txt /workspace
ENTRYPOINT python backuper.py