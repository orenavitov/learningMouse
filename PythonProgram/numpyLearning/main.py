import numpy
class test:
    def __init__(self, input):
        self.input = input

    def get_input(self):
        print(type(self.input))

input = numpy.random.random(size=[30, 2])
if __name__ == "__main__":
    t = test(input)
    t.get_input()