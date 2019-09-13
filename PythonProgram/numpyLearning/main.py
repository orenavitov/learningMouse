import copy


if __name__ == "__main__":
    a = [1, "2", 3, [4, 5]]
    b = a
    c = copy.copy(a)
    d = copy.deepcopy(a)
    e = a.copy()
    print("id of a:{0}".format(id(a)))
    print("id of a[0]:{0}".format(id(a[0])))
    print("id of a[1]:{0}".format(id(a[1])))
    print("id of a[2]:{0}".format(id(a[2])))
    print("id of a[3]:{0}".format(id(a[3])))


    print("id of c:{0}".format(id(c)))
    print("id of c[0]:{0}".format(id(c[0])))
    print("id of c[1]:{0}".format(id(c[1])))
    print("id of c[2]:{0}".format(id(c[2])))
    print("id of c[3]:{0}".format(id(c[3])))

    print("id of d:{0}".format(id(d)))
    print("id of d[0]:{0}".format(id(d[0])))
    print("id of d[1]:{0}".format(id(d[1])))
    print("id of d[2]:{0}".format(id(d[2])))
    print("id of d[3]:{0}".format(id(d[3])))

    print("id of e:{0}".format(id(e)))
    print("id of e[0]:{0}".format(id(e[0])))
    print("id of e[1]:{0}".format(id(e[1])))
    print("id of e[2]:{0}".format(id(e[2])))
    print("id of e[3]:{0}".format(id(e[3])))
