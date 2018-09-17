

from mih.sha1 import sha1

sha1 = sha1.sha1()

result = 'A:%s\nB:%s\nC:%s\nD:%s\nE:%s' % (sha1.A, sha1.B, sha1.C, sha1.D, sha1.E)
print(result)