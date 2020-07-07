import sys
inputs = []


for input in sys.stdin:
    input = input[:-1]

    input = input.replace(" ", "_")
    input = input.lower()

    print(input)
