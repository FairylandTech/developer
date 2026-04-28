# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-27 16:03:18 UTC+08:00
"""

from __future__ import annotations

import typing as t

import unittest

from service.knowledge import KnowledgeService


class MyTestCase(unittest.TestCase):
    input = "Hello, World!"

    def test_01_md5(self):
        result = KnowledgeService.md5(self.input)
        print(f"转换MD5结果：{result}")
        self.assertEqual("65a8e27d8879283831b664bd8b7f0ad4", result)

    def test_02_save_md5(self):
        KnowledgeService.save_md5(self.input)

    def test_03_check_md5(self):
        result = KnowledgeService.check_md5("65a8e27d8879283831b664bd8b7f0ad4")
        print(f"检查MD5结果：{result}")
        self.assertTrue(result)


if __name__ == "__main__":
    unittest.main()
