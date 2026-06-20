# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-06-20 21:36:20 UTC+08:00
"""

from __future__ import annotations

import os
import typing as t
import warnings

import orjson
import requests
from fake_useragent import UserAgent
from pydantic import BaseModel
from requests import Response

warnings.filterwarnings("ignore")


class AreaModel(BaseModel):
    code: str
    name: str
    leval: int
    parent_code: str


class Main:
    base_url = "https://dmfw.mca.gov.cn/9095/xzqh/getList?maxLevel=3"
    headers = {
        "User-Agent": UserAgent().random,
    }

    @classmethod
    def write_to_file(cls, data: t.Any):
        with open("dmfw.json", "wb") as stream:
            stream.write(orjson.dumps(data, option=orjson.OPT_INDENT_2))

    @classmethod
    def read_from_file(cls) -> dict[str, t.Any]:
        with open("dmfw.json", "rb") as stream:
            return orjson.loads(stream.read())

    @classmethod
    def recursive_parse_region(cls, region: dict[str, t.Any], parent_code: str = "") -> list[AreaModel]:
        code = region.get("code", "")
        name = region.get("name", "")
        leval = region.get("level", 0)

        area = AreaModel(code=code, name=name, leval=leval, parent_code=parent_code)

        sub_regions = region.get("children", [])
        if sub_regions is None:
            sub_regions = []

        sub_area_models = []
        for sub_region in sub_regions:
            sub_area_models.extend(cls.recursive_parse_region(sub_region, parent_code=code))

        return [area] + sub_area_models

    @classmethod
    def run(cls):
        if not os.path.exists("dmfw.json"):
            response: Response = requests.get(cls.base_url, headers=cls.headers, timeout=10, verify=False)
            response.raise_for_status()
            cls.write_to_file(response.json())

        data: list[dict[str, t.Any]] = cls.read_from_file().get("data", {}).get("children", [])

        result: list[AreaModel] = []
        for region in data:
            result.extend(cls.recursive_parse_region(region))

        for index, row in enumerate(result):
            print("insert into ubanda_area.area (code, name, parent_code, level) values ('{code}', '{name}', '{parent_code}', {leval});".format(**row.model_dump()))


if __name__ == "__main__":
    Main.run()
