import scrapy

class BilMuhSpiderXPath(scrapy.Spider):
    name = "bilmuh-xpath"
    starts_urls = ["http://bilmuh.mu.edu.tr/tr/personel/akademik",]
    def parse(self,response):
        for bilgi in response.xpath('//*[@id="icerik-metin"]/div/div/ul/li/div"]'):
            yield {
                'isim': bilgi.xpath('./span[@class="isim"]/text()').extract(),
            }
