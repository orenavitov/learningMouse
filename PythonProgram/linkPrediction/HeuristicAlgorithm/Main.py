from Tools import auc
import numpy
from Tools import process_gml_file
import json
from HeuristicAlgorithm.AA import AA
from HeuristicAlgorithm.ACT import ACT
from HeuristicAlgorithm.CN import CN
from HeuristicAlgorithm.HDI import HDI
from HeuristicAlgorithm.HPI import HPI
from HeuristicAlgorithm.Jaccard import jaccard
from HeuristicAlgorithm.Katz import Katz
from HeuristicAlgorithm.RW import RW
from HeuristicAlgorithm.RW import RWR
from HeuristicAlgorithm.RW import RW_Continuity
with open(r"./params.json", 'r') as file:
        params = json.load(file)
        data_set_name = params["data_set"]
        method = params["method"]
        radio = params["radio"]
        print("data_set_name : {0}".format(data_set_name))
        print("method : {0}".format(method))
        print("radio : {0}".format(radio))

file_address = r"../Data/{0}.gml".format(data_set_name)

G, A, nodes, all_neighbors, As = process_gml_file(file_address)

def get_test_A(A):
        node_number = A.shape[0]
        positives = []
        for i in range(node_number):
                for j in range(i, node_number):
                        if i != j:
                                label = A[i][j]
                                if label == 1:
                                        positives.append((i, j))
        numpy.random.shuffle(positives)
        positives_size = len(positives)
        positives_test = positives[0 : int(positives_size * radio)]
        A_test = numpy.zeros_like(A)
        for positive_test in positives_test:
                row = positive_test[0]
                col = positive_test[1]
                A_test[row][col] = 1
        return A_test

A_test = get_test_A(A)
results = []
if (method == "AA"):
        for i in range(5):
                auc = AA(A_test, A)
                print("the {0} time, the auc is : {1}".format(i + 1, auc))
                results.append(auc)

if (method == "ACT"):
        auc = ACT(A_test, A)
if (method == "CN"):
        auc = CN(A_test, A)

if (method == "HDI"):

        auc = HDI(A_test, A)

if (method == "HPI"):

        auc = HPI(A_test, A)

if (method == "Jaccard"):
        auc = jaccard(A_test, A)

if (method == "Katz"):
        auc = Katz(A_test, A)

if (method == "RW"):
        auc = RW(A_test, A)

if (method == "RWR"):
        auc = RWR(A_test, A)

if (method == "RW_Continuity"):
        auc = RW_Continuity(A_test, A)

print("data_set : {0}".format(data_set_name))
print("auc : {0}".format(auc))

