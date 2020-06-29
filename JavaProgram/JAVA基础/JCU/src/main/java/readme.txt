CAS:
需要对3个值进行操作：currentValue, value, nextValue
(1) currentValue = get(), 这一步会取现在的value;
(2) nextValue = currentValue + delta
(3) if (CompareAndSet(currentValue, nextValue)): 这一步会比较currentValue == value,
    如果相同, 将value设置为nextValue;