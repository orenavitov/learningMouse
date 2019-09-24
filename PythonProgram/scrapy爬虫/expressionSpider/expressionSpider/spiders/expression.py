import re
import scrapy
from scrapy.loader import ItemLoader
from selenium import  webdriver

class expressionSpiderData(scrapy.Item):

    url = scrapy.Field()
    state = scrapy.Field()
    expressionUrls = scrapy.Field()

class ExpressionSpider(scrapy.Spider):

    name = "expression"

    def __init__(self):
        # 使用安装的浏览器
        self.browser = webdriver.Firefox()

    def __del__(self):
        self.browser.close()

    def start_requests(self):
        urls = ['https://tieba.baidu.com/f?ie=utf-8&kw=%E6%96%97%E5%9B%BE']
        for url in urls:
            yield scrapy.Request(url=url, callback=self.parse)

    def parse(self, response):
        #每个帖子的链接格式
        urlPattern = re.compile('https://tieba.baidu.com/p/.*')

        # 启动浏览器
        self.browser.get(response.url)
        # 查找所有的 href元素
        try:
            aText = self.browser.find_elements_by_xpath('//a[@href]')
            print('root url: %s' %(response.url))
            for a in aText:
                try:
                    url = a.get_property('href')
                    print('url: %s' %(url))
                    matchedUrl = urlPattern.match(url).group(0)
                    if matchedUrl:
                        print('matchedUrl: %s' %(matchedUrl))
                        yield scrapy.Request(url = matchedUrl, callback = self.expressionHandler)
                except Exception as e:
                    print("无效链接：%s" % (url))
        except Exception as e:
            print(e)

    def expressionHandler(self, response):
        # 过滤所有的png、 jpg图片
        pngPattern = re.compile('(.*).png')
        jpgPattern = re.compile('(.*).jpg')

        item = ItemLoader(item=expressionSpiderData, response=response)
        item.add_value('url', response.url)
        item.add_value('state', response.status)
        imgElements = self.browser.find_elements_by_xpath('//img[@src]')
        try:
            for imgElemnt in imgElements:
                imgSrc = imgElemnt.get_property('src')
                if pngPattern.match(imgSrc):
                    pngUrl = pngPattern.match(imgSrc).group(0)
                    print('PNGSrc: %s' %(pngUrl))
                if jpgPattern.match(imgSrc):
                    jpgUrl = jpgPattern.match(imgSrc).group(0)
                    print('JPGSrc: %s' %(jpgUrl))

        except Exception as e:
            print(e)
        yield item