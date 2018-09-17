import scrapy
class QuotesSpider(scrapy.Spider):
    # name must be unique
    name = "quotes"
    def start_requests(self):
        urls = [
            'http://quotes.toscrape.com/page/1/',
            'http://quotes.toscrape.com/page/2/'
        ]
        """
        Scrapy schedules the scrapy.Request objects returned by the start_requests method of the Spider. 
        Upon receiving a response for each one, it instantiates Response objects and calls the callback method 
        associated with the request (in this case, the parse method) passing the response as argument.
        """
        for url in urls:
            yield scrapy.Request(url = url, callback=self.parse)

    def parse(self, response):
        page = response.url.split("/")[2]
        filename = 'quotes-%s' %page
        with open(filename, 'wb') as f:
            f.write(response.body)
        self.log('Saved file %s' %filename)


