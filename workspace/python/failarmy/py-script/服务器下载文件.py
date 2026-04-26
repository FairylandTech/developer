# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-26 15:52:45 UTC+08:00
"""
from __future__ import annotations

import typing as t

from scp import SCPClient
import paramiko


def main():
    hostname = "x.x.x.x.host"
    port = 22
    username = "x"
    password = "x%EQ6t$H"

    client = paramiko.SSHClient()
    client.load_system_host_keys()
    client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    client.connect(hostname, port, username, password)

    with SCPClient(client.get_transport()) as scp:
        scp.get("/etc/postgresql/16/main/postgresql.conf", "./output/postgresql.conf")

    client.close()


if __name__ == '__main__':
    main()
