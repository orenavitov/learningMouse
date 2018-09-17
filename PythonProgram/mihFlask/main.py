from flask import Flask
from mihFlask.config import DevConfig
from flask import request
import json
app = Flask(__name__)
app.config.from_object(DevConfig)

@app.route('/mih', methods=['POST'])
def home():
    jsonData = request.data
    jsonString = jsonData.decode('utf-8')
    data = json.loads(jsonString)
    return 'hello world'

if __name__ == '__main__':
    app.run()
