import array
import numbers
import reprlib
import functools
class test:
    _typeCode = 'd'
    def __init__(self, items):
        self._list = array.array(self._typeCode, items)

    def __iter__(self):
        return iter(self._list)

    def __repr__(self):
        list = reprlib.repr(self._list)
        list = list[list.find('['):-1]
        return 'Vector({0})'.format(list)

    def __len__(self):
        return len(self._list)
    def __getitem__(self, index):
        cls = type(self)
        if isinstance(index, slice):
            return cls(self._list[index])
        elif isinstance(index, numbers.Integral):
            return self._list[index]
        return self._list[index]

if __name__ == '__main__':
    print(test._typeCode)