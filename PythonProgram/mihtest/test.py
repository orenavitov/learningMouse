
import re
import sys
import subprocess
import pdfkit
if __name__ == '__main__':
    inputfile = sys.argv[1].replace(" ", "\ ")
    temp_html = inputfile[0:inputfile.rfind('.')] + '.html'
    command = 'ipython nbconvert --to html ' + inputfile
    subprocess.call(command, shell=True)
    print('============success===========')
    output_file = inputfile[0:inputfile.rfind('.')] + '.pdf'
    pdfkit.from_file(temp_html, output_file)
    subprocess.call('rm ' + temp_html, shell=True)
