# -*- coding: utf-8 -*-
import scrapy


class ToScrapeCSSSpider(scrapy.Spider):
    name = "bilmuh-css"
    allowed_domains =["mu.edu.tr"]
    start_urls = [
        "http://bilmuh.mu.edu.tr/tr/personel/akademik","http://ee.mu.edu.tr/tr/personel/akademik","http://ce.mu.edu.tr/tr/personel/akademik","http://jeoloji.mu.edu.tr/tr/personel/akademik",
        "http://maden.mu.edu.tr/tr/personel/akademik","http://metalurji.mu.edu.tr/tr/personel/akademik",
    ]

    def parse(self, response):
        link = {}
        #yield{'title' : response.css("title::text").extract()}
        for bilgi in response.css("div.bilgi"):
            deneme =  bilgi.css("span.detay a::attr(href)").extract()[0]
            link[response.css("title::text").extract_first()] = deneme
            """yield {
                'title' : response.css("title::text").extract_first(),
                'isim': bilgi.css("span.isim::text").extract(),
                'unvan':bilgi.css("span.unvan::text").extract(),
                'link':bilgi.css("span.detay a::attr(href)").extract(),

            }"""
            for i in link:
                request = scrapy.Request(link[i],
                             callback=self.parse_user)
                request.meta['link'] = link
                yield request
    def parse_user(self,response):
        link = response.meta['link']
        item = {'name':" ",'faculty':" ",'department':" ",'staff':" ",'email':" ",'publications':" ",'year':" "}
        for i in range(1,len(response.xpath('//*[@id="ContentPlaceHolder1_Personel1_lbl_yayinlar"]/div/div[2]/div'))):
            for x in range(1,len(response.xpath('//*[@id="ContentPlaceHolder1_Personel1_lbl_yayinlar"]/div/div[2]/div['+str(i)+']/div'))):

                #'title' : response.css("title::text").extract_first(),
                item['name'] = response.xpath('//*[@id="ContentPlaceHolder1_Personel1_lbl_name"]/text()').extract_first()
                item['faculty'] = response.xpath('//*[@id="ContentPlaceHolder1_Personel1_lbl_kadro"]/div/div[2]/div[1]/div/text()').extract_first()
                item['department'] = response.xpath('//*[@id="ContentPlaceHolder1_Personel1_lbl_kadro"]/div/div[2]/div[2]/div/text()').extract()[0].split()[3] + " " + response.xpath('//*[@id="ContentPlaceHolder1_Personel1_lbl_kadro"]/div/div[2]/div[2]/div/text()').extract()[0].split()[4]


                item['staff'] = response.xpath('//*[@id="ContentPlaceHolder1_Personel1_lbl_kadro"]/div/div[2]/div[2]/div/text()').extract_first()
                item['email'] = response.xpath('//*[@id="ContentPlaceHolder1_Personel1_lbl_contact"]/div[1]/div[2]/text()').extract_first()
                item['publications'] = response.xpath('//*[@id="ContentPlaceHolder1_Personel1_lbl_yayinlar"]/div/div[2]/div['+str(i)+']/div['+str(x)+']/text()').extract()
                item['img'] = response.xpath('//*[@id="ContentPlaceHolder1_Personel1_lbl_image"]/img/@src').extract()
                data = str(response.xpath('//*[@id="ContentPlaceHolder1_Personel1_lbl_yayinlar"]/div/div[2]/div['+str(i)+']/div['+str(x)+']/text()').extract()[0]).replace('.',' ').replace('-',' ').replace('/',' ').replace(',',' ').split()
                #data2 = data.split()
                """for d in data:
                    data2 = data.replace('.',' ').replace('-',' ').replace('/',' ')"""
                for d in data:
                	try:
                		if (type(int(d))) is int and len(d)==4:
                			item['year']=  d

                	except ValueError:
                		print("Value is not integer")

                if item['publications'] == " ":
                    print("Boş alanlar var")
                else:
                    yield item


                                #yield{'year' : int(response.xpath('//*[@id="ContentPlaceHolder1_Personel1_lbl_yayinlar"]/div/div[2]/div['+str(i)+']/div['+str(x)+']/text()').extract()[0].split()[-1]),}


              # 'publications' : response.xpath('//*[@id="ContentPlaceHolder1_Personel1_lbl_yayinlar"]/div/div[2]/div/div/text()').extract(),



        #"""for i in response.xpath('//*[@id="ContentPlaceHolder1_Personel1_lbl_yayinlar"]/div/div[2]/div/text()'):
         #  for x in response.xpath('//*[@id="ContentPlaceHolder1_Personel1_lbl_yayinlar"]/div/div[2]/div/div/text()'):
        #       yield{'publication' :  response.xpath('//*[@id="ContentPlaceHolder1_Personel1_lbl_yayinlar"]/div/div[2]/div[i]/div[x]/text()').extract(), }""""

        next_page_url = response.css("li.next > a::attr(href)").extract_first()
        if next_page_url is not None:
            yield scrapy.Request(response.urljoin(next_page_url))
