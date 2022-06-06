FROM python:3.9.13-alpine3.16

RUN pip install gcloud

RUN apk add --no-cache postgresql-client

RUN mkdir -p /workspace

WORKDIR /workspace

COPY backuper.py /workspace

ENTRYPOINT python backuper.py