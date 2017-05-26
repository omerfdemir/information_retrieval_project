# -*- coding: utf-8 -*-
import scrapy

class Mu2SpiderXPath(scrapy.Spider):
    name = 'sites2-xpath'
    start_urls = ["http://eski.mu.edu.tr/",]
    def parse(self,response):
        for data in response.xpath('//[@id="link3"]/div[2]/div[2]/div/ul/li/a'):
            yield {
                'isim': data.xpath('./text()').extract(),
                'links': data.xpath('./@href').extract()
                    }
