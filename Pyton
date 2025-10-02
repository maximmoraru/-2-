БИНАРНАЯ КУЧА
import heapq

class BinaryHeap:
    def __init__(self):
        self.heap = []
    
    def push(self, val):
        heapq.heappush(self.heap, val)
    
    def pop(self):
        return heapq.heappop(self.heap) if self.heap else None
    
    def peek(self):
        return self.heap[0] if self.heap else None
    
    def size(self):
        return len(self.heap)
    
    def __str__(self):
        return str(self.heap)

# Демонстрация
print("=== Бинарная куча на Python ===")
heap = BinaryHeap()
data = [8, 3, 5, 1, 6, 2, 4, 7]
for num in data:
    heap.push(num)

print(f"Куча: {heap}")
print(f"Минимальный элемент: {heap.peek()}")
print(f"Извлечение: {heap.pop()}")
print(f"Куча после извлечения: {heap}")


реализация бинарной кучи:
class CustomBinaryHeap:
    def __init__(self):
        self.heap = []
    
    def _parent(self, i):
        return (i - 1) // 2
    
    def _left_child(self, i):
        return 2 * i + 1
    
    def _right_child(self, i):
        return 2 * i + 2
    
    def _swap(self, i, j):
        self.heap[i], self.heap[j] = self.heap[j], self.heap[i]
    
    def push(self, val):
        self.heap.append(val)
        self._sift_up(len(self.heap) - 1)
    
    def _sift_up(self, i):
        while i > 0 and self.heap[i] < self.heap[self._parent(i)]:
            parent = self._parent(i)
            self._swap(i, parent)
            i = parent
    
    def pop(self):
        if not self.heap:
            return None
        
        min_val = self.heap[0]
        last = self.heap.pop()
        
        if self.heap:
            self.heap[0] = last
            self._sift_down(0)
        
        return min_val
    
    def _sift_down(self, i):
        size = len(self.heap)
        while True:
            left = self._left_child(i)
            right = self._right_child(i)
            smallest = i
            
            if left < size and self.heap[left] < self.heap[smallest]:
                smallest = left
            if right < size and self.heap[right] < self.heap[smallest]:
                smallest = right
            
            if smallest != i:
                self._swap(i, smallest)
                i = smallest
            else:
                break
    
    def __str__(self):
        return str(self.heap)

print("\n=== Собственная реализация бинарной кучи ===")
custom_heap = CustomBinaryHeap()
for num in [8, 3, 5, 1, 6, 2]:
    custom_heap.push(num)

print(f"Куча: {custom_heap}")
print(f"Извлечение: {custom_heap.pop()}")
print(f"Куча после извлечения: {custom_heap}")




Куча Фибоначчи
class FibonacciNode:
    def __init__(self, key, value=None):
        self.key = key
        self.value = value
        self.degree = 0
        self.parent = None
        self.child = None
        self.left = self
        self.right = self
        self.marked = False

class FibonacciHeap:
    def __init__(self):
        self.min_node = None
        self.count = 0
        self.nodes = {}  # Для поиска по ключу
    
    def insert(self, key, value=None):
        node = FibonacciNode(key, value)
        if self.min_node is None:
            self.min_node = node
        else:
            # Добавляем node в корневой список
            self._add_to_root_list(node)
            if key < self.min_node.key:
                self.min_node = node
        self.count += 1
        self.nodes[key] = node
        return node
    
    def _add_to_root_list(self, node):
        node.left = self.min_node.left
        node.right = self.min_node
        self.min_node.left.right = node
        self.min_node.left = node
    
    def extract_min(self):
        if self.min_node is None:
            return None
        
        min_node = self.min_node
        min_key = min_node.key
        
        # Добавляем детей min_node в корневой список
        if min_node.child is not None:
            child = min_node.child
            while True:
                next_child = child.right
                self._add_to_root_list(child)
                child.parent = None
                child = next_child
                if child == min_node.child:
                    break
        
        # Удаляем min_node из корневого списка
        min_node.left.right = min_node.right
        min_node.right.left = min_node.left
        
        if min_node == min_node.right:
            self.min_node = None
        else:
            self.min_node = min_node.right
            self._consolidate()
        
        self.count -= 1
        del self.nodes[min_key]
        return min_key
    
    def _consolidate(self):
        if self.min_node is None:
            return
        
        degree_table = {}
        nodes_to_process = []
        current = self.min_node
        
        # Собираем все корневые узлы
        while True:
            nodes_to_process.append(current)
            current = current.right
            if current == self.min_node:
                break
        
        for node in nodes_to_process:
            degree = node.degree
            while degree in degree_table:
                other = degree_table[degree]
                if node.key > other.key:
                    node, other = other, node
                self._link(other, node)
                del degree_table[degree]
                degree += 1
            degree_table[degree] = node
        
        # Восстанавливаем min_node
        self.min_node = None
        for node in degree_table.values():
            if self.min_node is None or node.key < self.min_node.key:
                self.min_node = node
    
    def _link(self, child, parent):
        # Удаляем child из корневого списка
        child.left.right = child.right
        child.right.left = child.left
        
        # Делаем child дочерним узлом parent
        child.parent = parent
        if parent.child is None:
            parent.child = child
            child.right = child
            child.left = child
        else:
            child.left = parent.child.left
            child.right = parent.child
            parent.child.left.right = child
            parent.child.left = child
        
        parent.degree += 1
        child.marked = False
    
    def decrease_key(self, key, new_key):
        if new_key > key:
            raise ValueError("Новый ключ должен быть меньше текущего")
        
        node = self.nodes.get(key)
        if node is None:
            raise KeyError("Узел не найден")
        
        node.key = new_key
        parent = node.parent
        
        if parent is not None and node.key < parent.key:
            self._cut(node, parent)
            self._cascading_cut(parent)
        
        if node.key < self.min_node.key:
            self.min_node = node
    
    def _cut(self, node, parent):
        # Удаляем node из списка детей parent
        if node.right == node:
            parent.child = None
        else:
            node.left.right = node.right
            node.right.left = node.left
            if parent.child == node:
                parent.child = node.right
        
        parent.degree -= 1
        self._add_to_root_list(node)
        node.parent = None
        node.marked = False
    
    def _cascading_cut(self, node):
        parent = node.parent
        if parent is not None:
            if not node.marked:
                node.marked = True
            else:
                self._cut(node, parent)
                self._cascading_cut(parent)
    
    def __len__(self):
        return self.count
    
    def is_empty(self):
        return self.count == 0

# Демонстрация
print("\n=== Куча Фибоначчи на Python ===")
fib_heap = FibonacciHeap()
fib_heap.insert(10, "A")
fib_heap.insert(5, "B")
fib_heap.insert(15, "C")
fib_heap.insert(3, "D")
fib_heap.insert(7, "E")

print(f"Минимальный элемент: {fib_heap.extract_min()}")
print(f"Следующий минимальный: {fib_heap.extract_min()}")
print(f"Размер кучи: {len(fib_heap)}")




Хеш-таблица
class HashTable:
    def __init__(self, size=10, load_factor=0.75):
        self.size = size
        self.load_factor = load_factor
        self.count = 0
        self.table = [[] for _ in range(size)]
    
    def _hash(self, key):
        return hash(key) % self.size
    
    def _resize(self):
        old_table = self.table
        self.size *= 2
        self.table = [[] for _ in range(self.size)]
        self.count = 0
        
        for bucket in old_table:
            for key, value in bucket:
                self.put(key, value)
    
    def put(self, key, value):
        if self.count / self.size >= self.load_factor:
            self._resize()
        
        index = self._hash(key)
        bucket = self.table[index]
        
        # Проверяем, есть ли уже такой ключ
        for i, (k, v) in enumerate(bucket):
            if k == key:
                bucket[i] = (key, value)
                return
        
        # Добавляем новую пару
        bucket.append((key, value))
        self.count += 1
    
    def get(self, key):
        index = self._hash(key)
        bucket = self.table[index]
        
        for k, v in bucket:
            if k == key:
                return v
        
        raise KeyError(f"Key '{key}' not found")
    
    def remove(self, key):
        index = self._hash(key)
        bucket = self.table[index]
        
        for i, (k, v) in enumerate(bucket):
            if k == key:
                del bucket[i]
                self.count -= 1
                return
        
        raise KeyError(f"Key '{key}' not found")
    
    def contains(self, key):
        try:
            self.get(key)
            return True
        except KeyError:
            return False
    
    def __str__(self):
        result = []
        for i, bucket in enumerate(self.table):
            if bucket:
                result.append(f"Bucket {i}: {bucket}")
        return "\n".join(result)
    
    def __len__(self):
        return self.count

# Демонстрация
print("\n=== Хеш-таблица на Python ===")
hash_table = HashTable()

# Добавление элементов
data = [
    ("apple", 5),
    ("banana", 3),
    ("orange", 7),
    ("grape", 2),
    ("kiwi", 8),
    ("mango", 4)
]

for key, value in data:
    hash_table.put(key, value)

print("Хеш-таблица после добавления:")
print(hash_table)
print(f"Количество элементов: {len(hash_table)}")

# Поиск элементов
print(f"\nЗначение для 'apple': {hash_table.get('apple')}")
print(f"Содержит 'banana': {hash_table.contains('banana')}")

# Обновление значения
hash_table.put("apple", 10)
print(f"Обновлённое значение для 'apple': {hash_table.get('apple')}")

# Удаление элемента
hash_table.remove("banana")
print(f"\nПосле удаления 'banana', количество элементов: {len(hash_table)}")
print(f"Содержит 'banana': {hash_table.contains('banana')}")
