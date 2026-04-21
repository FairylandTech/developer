# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-21 03:53:11 UTC+08:00
"""
from __future__ import annotations

import typing as t

import grpc

from service.grpc.welcome import Welcome_pb2
from service.grpc.welcome import Welcome_pb2_grpc


def run():
    with grpc.insecure_channel("localhost:8089") as channel:
        stub = Welcome_pb2_grpc.WelcomeServiceStub(channel)

        response = stub.welcome(Welcome_pb2.WelcomeRequest(name="Lionel"))
