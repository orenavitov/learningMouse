import os

class sha1:
    """

    """
    A=0x67452301
    B=0xEFCDAB89
    C=0x98BADCFE 
    D=0x10325476
    E=0xC3D2E1F0
    Atemp = A
    Btemp = B
    Ctemp = C
    Dtemp = D
    Etemp = E

    def __init__(self):
        partList = sha1.processFile('C:/Users/米昊/Desktop/sha1.txt')
        extensionChildParts = []
        for part in partList:
            childPart = sha1.childPartFactory(part)
            extensionChildParts.extend(sha1.processChildParts(childPart))
        sha1.finalProcess(extensionChildParts)

        
    def processFile(filePath):
        file = open(filePath, 'r+', encoding = 'utf-8').read()

        length = len(file)
        appendNumber = 512 - length % 512
        processedFile = '%s%s%s' %(file, '1', '0'*(appendNumber - 1))
        partNumber = int(len(processedFile) / 512)
        L = []
        while(partNumber > 0):
            i = 0
            start = 0 + 512 * i
            end = 511 + 512 * i
            part = processedFile[start: end]
            L.append(part)
            partNumber = partNumber - 1
        return L

    def childPartFactory(part):
        M = []
        for i in range(0, 16):
            start = 0 + 32 * i
            end = 31 + 32 * i
            childPart = part[start: end]
            M.append(childPart)
        return M

    def processChildParts(childParts):
        extensionChildParts = childParts
        for i in range(16, 81):
            temp = (int(extensionChildParts[i - 3]) ^ int(extensionChildParts[i - 8])
            ^ int(extensionChildParts[i - 14]) ^ int(extensionChildParts[i - 16]))
            temp = sha1.loopLeft(temp, 1)
            extensionChildParts.append(temp)
        return extensionChildParts
    
    def loopRight(input, k):
        l = str(input).split('')
        length = len(l)
        output = '%s%s' %(l[length - k:], l[:length - k]) 
        return output

    def loopLeft(input, k):
        binary = bin(input).replace('0b', '')
        result = '%s%s' %(binary[k:], binary[:k])
        output = int(result, 2)
        return output
    def finalProcess(extensionChildParts):
        k0to19 = 0x5A827999
        k20to39 = 0x6ED9EBA1
        k40to59 = 0x8F188CDC
        k60to79 = 0xCA62C1D6
        for i in range(0, 80):
            tempA = sha1.A
            tempB = sha1.B
            tempC = sha1.C
            tempD = sha1.D
            tempE = sha1.E
            if (i >= 0 & i <= 19):
                sha1.A = sha1.loopLeft(tempA, 5) + ((tempB & tempC) | (tempA & tempD)) + tempE + int(extensionChildParts[i]) + k0to19
            if (i >= 20 & i <= 39):
                sha1.A = sha1.loopLeft(tempA, 5) + (tempB ^ tempC ^ tempC) + tempE + int(extensionChildParts[i]) + k20to39
            if (i >= 40 & i <= 59):
                sha1.A = sha1.loopLeft(tempA, 5) + ((tempB & tempC) | (tempB & tempD) | (tempC & tempD)) + tempE + int(extensionChildParts[i]) + k40to59
            if (i >= 60 & i <= 79):
                sha1.A = sha1.loopLeft(tempA, 5) + (tempB ^ tempC ^ tempC) + tempE + int(extensionChildParts[i]) + k60to79

            sha1.B = tempA
            sha1.C = sha1.loopLeft(tempB, 30)
            sha1.D = tempC
            sha1.E = tempD
        sha1.A = sha1.Atemp + sha1.A
        sha1.B = sha1.Btemp + sha1.B
        sha1.C = sha1.Ctemp + sha1.C
        sha1.D = sha1.Dtemp + sha1.D
        sha1.E = sha1.Etemp + sha1.E




    

    




