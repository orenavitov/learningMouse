# HashMap

HashMap种的Key, Value是以Node的形式存储的， 所有的Node存储成table

```
transient Node<K,V>[] table;
```

## put

```
public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }

final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        // 首先获取当前table的长度， 如果当前的table为空， 则声称一个table数组， 使用的是resize();
        if ((tab = table) == null || (n = tab.length) == 0)
            // resize()负责动态的改变table数组的长度
            n = (tab = resize()).length;
        // i = (n - 1) & hash 为散列算法，当散列到的位置为空时，直接将这个位置插入新的Node;
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
        // 如果当前散列的位置有Node
        else {
            Node<K,V> e; K k;
            // 如果当前位置的Node的hash值和要插入节点的hash值相同并且Key值也相同， 保存当前的节点， 后续会将
            // 当前节点的value值设置为新传入函数的value值
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            // 如果不满足上述条件
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                // 将新的节点插入到table哈希索引的尾部
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        // 当链表长度大于TREEIFY_THRESHOLD转为Red-Black-Tree存储，TREEIFY_THRESHOLD默认为8
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    // 如果在table的哈希索引的链表种有相同的具有key的node，直接结束；
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }

final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        // threshold为扩展阈值，threshold = capacity * load factor, 即扩展阈值为容量乘加载因子
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        }
        else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
        @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }

```

## get

```
public V get(Object key) {
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
        // table不能为null， 否则表示这个HashMap为空， 直接返回null
        if ((tab = table) != null && (n = tab.length) > 0 &&
            // 根据(n - 1) & hash，在相应的哈希索引处找到第一个Node
            (first = tab[(n - 1) & hash]) != null) {
            // 如果第一个Node的哈希值和要寻找的hash值相同，并且key值相同，直接返回
            if (first.hash == hash && // always check first node
                ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            // 如果上述条件不成立，也就是第一个Node不满足条件，从第一个节点的next往下找；
            if ((e = first.next) != null) {
                if (first instanceof TreeNode)
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }

```

# ArrayList

## 构造方法

```
// 可以看出无参的构造方法就是将elementData设置为一个Object空数组
private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

// 有参的构造方法是将elementData设置成一个指定容量大小的Object数组
public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        }
    }

```

## add

```
public boolean add(E e) {
        // modCount 记录更改的次数，用于快速失败
        modCount++;
        // size为记录elementData数组中的元素数量， 也就是下一个要插入的位置
        add(e, elementData, size);
        return true;
    }

private void add(E e, Object[] elementData, int s) {
        // 如果elementData已经插满
        if (s == elementData.length)
            // 对elementData进行容量扩展
            elementData = grow();
        elementData[s] = e;
        size = s + 1;
    }

private Object[] grow() {
        return grow(size + 1);
    }

private Object[] grow(int minCapacity) {
        return elementData = Arrays.copyOf(elementData,
                                           newCapacity(minCapacity));
    }

```

## get

```

public E get(int index) {
        // 判断是否越界
        Objects.checkIndex(index, size);
        // 直接返回数组下标为index元素
        return elementData(index);
    }
```

## remove

```
// 移除object
public boolean remove(Object o) {
        final Object[] es = elementData;
        final int size = this.size;
        int i = 0;
        // 都是寻找一个object对应的下标i
        found: {
            if (o == null) {
                for (; i < size; i++)
                    if (es[i] == null)
                        break found;
            } else {
                for (; i < size; i++)
                    if (o.equals(es[i]))
                        break found;
            }
            return false;
        }
        fastRemove(es, i);
        return true;
    }

// 移除第下标为i的元素
public E remove(int index) {
        Objects.checkIndex(index, size);
        final Object[] es = elementData;

        @SuppressWarnings("unchecked") E oldValue = (E) es[index];
        fastRemove(es, index);

        return oldValue;
    }

private void fastRemove(Object[] es, int i) {
        modCount++;
        final int newSize;
        if ((newSize = size - 1) > i)
            System.arraycopy(es, i + 1, es, i, newSize - i);
        es[size = newSize] = null;
    }
```

## ConcurrentHashMap

使用Node存储key、value

```
    transient volatile Node<K,V>[] table;
```

## 构造方法

```
// 无参构造方法
public ConcurrentHashMap() {
    }

// 
public ConcurrentHashMap(int initialCapacity) {
        this(initialCapacity, LOAD_FACTOR, 1);
    }

// 
public ConcurrentHashMap(int initialCapacity,
                             float loadFactor, int concurrencyLevel) {
        if (!(loadFactor > 0.0f) || initialCapacity < 0 || concurrencyLevel <= 0)
            throw new IllegalArgumentException();
        if (initialCapacity < concurrencyLevel)   // Use at least as many bins
            initialCapacity = concurrencyLevel;   // as estimated threads
        long size = (long)(1.0 + (long)initialCapacity / loadFactor);
        int cap = (size >= (long)MAXIMUM_CAPACITY) ?
            MAXIMUM_CAPACITY : tableSizeFor((int)size);
        this.sizeCtl = cap;
    }

```

## put

```
public V put(K key, V value) {
        return putVal(key, value, false);
    }

final V putVal(K key, V value, boolean onlyIfAbsent) {
        if (key == null || value == null) throw new NullPointerException();
        // 获得key的哈希值
        int hash = spread(key.hashCode());
        int binCount = 0;
        for (Node<K,V>[] tab = table;;) {
            Node<K,V> f; int n, i, fh; K fk; V fv;
            if (tab == null || (n = tab.length) == 0)
                tab = initTable();
            // 如果通过散列在i位置没有Node，通过new Node<K,V>(hash, key, value) 生成一个新的Node
            // 直接通过CAS的方式将table的下标为i的位置设置成上述生成的新的Node, 这里直接使用了
            // Unsafe的compareAndSetObject
            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
                if (casTabAt(tab, i, null, new Node<K,V>(hash, key, value)))
                    break;                   // no lock when adding to empty bin
            }
            else if ((fh = f.hash) == MOVED)
                tab = helpTransfer(tab, f);
            // 如果在table的i下标处的Node和key的哈希值相同， 并且key值相同，则将此处的Node中的value直接返回
            else if (onlyIfAbsent // check first node without acquiring lock
                     && fh == hash
                     && ((fk = f.key) == key || (fk != null && key.equals(fk)))
                     && (fv = f.val) != null)
                return fv;
            else {
                V oldVal = null;
                synchronized (f) {
                    // ？
                    if (tabAt(tab, i) == f) {
                        if (fh >= 0) {
                            binCount = 1;
                            for (Node<K,V> e = f;; ++binCount) {
                                K ek;
                                // 从table的i下标处开始，遍历这个下标后的链表，找到一个和输入的key哈希值
                                // 相同， key相同的节点，将这个节点的value设置成输入的value
                                if (e.hash == hash &&
                                    ((ek = e.key) == key ||
                                     (ek != null && key.equals(ek)))) {
                                    oldVal = e.val;
                                    if (!onlyIfAbsent)
                                        e.val = value;
                                    break;
                                }
                                // 如果到最后一个节点都没有找到，生成一个新的节点进行插入
                                Node<K,V> pred = e;
                                if ((e = e.next) == null) {
                                    pred.next = new Node<K,V>(hash, key, value);
                                    break;
                                }
                            }
                        }
                        else if (f instanceof TreeBin) {
                            Node<K,V> p;
                            binCount = 2;
                            if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
                                                           value)) != null) {
                                oldVal = p.val;
                                if (!onlyIfAbsent)
                                    p.val = value;
                            }
                        }
                        else if (f instanceof ReservationNode)
                            throw new IllegalStateException("Recursive update");
                    }
                }
                if (binCount != 0) {
                    if (binCount >= TREEIFY_THRESHOLD)
                        treeifyBin(tab, i);
                    if (oldVal != null)
                        return oldVal;
                    break;
                }
            }
        }
        addCount(1L, binCount);
        return null;
    }
```

# CopyOnWriteArrayList

在进行set、add、remove操作是先将CopyOnWriteArrayList中的Object[] array进行clone，然后进行之后的操作

## set

```
public E set(int index, E element) {
        synchronized (lock) {
            Object[] es = getArray();
            E oldValue = elementAt(es, index);
            // 如果之前位置上的元素和要set的element不同，进行COW
            if (oldValue != element) {
                es = es.clone();
                es[index] = element;
                setArray(es);
            }
            return oldValue;
        }
    }
```

## add

```
public boolean add(E e) {
        synchronized (lock) {
            Object[] es = getArray();
            int len = es.length;
            es = Arrays.copyOf(es, len + 1);
            es[len] = e;
            setArray(es);
            return true;
        }
    }

public void add(int index, E element) {
        synchronized (lock) {
            Object[] es = getArray();
            int len = es.length;
            if (index > len || index < 0)
                throw new IndexOutOfBoundsException(outOfBounds(index, len));
            Object[] newElements;
            int numMoved = len - index;
            if (numMoved == 0)
                newElements = Arrays.copyOf(es, len + 1);
            else {
                newElements = new Object[len + 1];
                System.arraycopy(es, 0, newElements, 0, index);
                System.arraycopy(es, index, newElements, index + 1,
                                 numMoved);
            }
            newElements[index] = element;
            setArray(newElements);
        }
    }

```

## remove

```
public E remove(int index) {
        synchronized (lock) {
            Object[] es = getArray();
            int len = es.length;
            E oldValue = elementAt(es, index);
            int numMoved = len - index - 1;
            Object[] newElements;
            if (numMoved == 0)
                newElements = Arrays.copyOf(es, len - 1);
            else {
                newElements = new Object[len - 1];
                System.arraycopy(es, 0, newElements, 0, index);
                System.arraycopy(es, index + 1, newElements, index,
                                 numMoved);
            }
            setArray(newElements);
            return oldValue;
        }
    }
```
