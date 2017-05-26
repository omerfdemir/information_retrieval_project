# -*- coding: utf-8 -*-
import scrapy

class MuSpiderXPath(scrapy.Spider):
    name = 'sites-xpath'
    start_urls = ["mu.edu.tr",]
    def parse(self,response):
        for data in response.xpath('//div/ul/li[3]/div/div[2]/ul/li'):
            yield {
                'isim': data.xpath('a/text()').extract(),
                'links': data.xpath('a/@href').extract()
                    }
