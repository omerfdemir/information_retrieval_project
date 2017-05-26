# -*- coding: utf-8 -*-
import scrapy

class MuSpiderXPath(scrapy.Spider):
    name = 'faculties-xpath'
    start_urls = ["http://www.gsf.mu.edu.tr","http://www.edebiyat.mu.edu.tr","http://www.egitim.mu.edu.tr","http://www.fen.mu.edu.tr","http://www.fethiye.mu.edu.tr",
    "http://www.fsyo.mu.edu.tr","http://www.iibf.mu.edu.tr","http://www.ilahiyat.mu.edu.tr","http://www.muhendislik.mu.edu.tr","http://www.msyo.mu.edu.tr",
    "http://www.sporbilimleri.mu.edu.tr","http://www.sufak.mu.edu.tr","http://www.tef.mu.edu.tr","http://www.teknoloji.mu.edu.tr","http://www.tip.mu.edu.tr","http://www.turizm.mu.edu.tr",
    ]
    def parse(self,response):
        for data in response.xpath('//*[@id="lbl_menu"]/li[5]/ul/li'):
            yield {
                'title' : response.css("title::text").extract_first(),
                'isim': data.xpath('a/text()').extract_first(),
                'links': data.xpath('a/@href').extract()
                    }
