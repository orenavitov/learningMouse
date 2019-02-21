from scrapy import cmdline



if __name__ == '__main__':
    name = 'expression'

    cmd = 'scrap crawl {}'.format(name)

    cmdline.execute(cmd.split())