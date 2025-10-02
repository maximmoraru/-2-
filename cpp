реализация бинарной кучи
#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

class MinHeap {
private:
    std::vector<int> heap;
    
    int parent(int i) { return (i - 1) / 2; }
    int left(int i) { return 2 * i + 1; }
    int right(int i) { return 2 * i + 2; }
    
    void heapifyUp(int i) {
        while (i > 0 && heap[i] < heap[parent(i)]) {
            std::swap(heap[i], heap[parent(i)]);
            i = parent(i);
        }
    }
    
    void heapifyDown(int i) {
        int smallest = i;
        int l = left(i);
        int r = right(i);
        
        if (l < heap.size() && heap[l] < heap[smallest])
            smallest = l;
        if (r < heap.size() && heap[r] < heap[smallest])
            smallest = r;
        
        if (smallest != i) {
            std::swap(heap[i], heap[smallest]);
            heapifyDown(smallest);
        }
    }

public:
    void push(int key) {
        heap.push_back(key);
        heapifyUp(heap.size() - 1);
    }
    
    int pop() {
        if (heap.empty()) return -1;
        
        int minVal = heap[0];
        heap[0] = heap.back();
        heap.pop_back();
        
        if (!heap.empty())
            heapifyDown(0);
        
        return minVal;
    }
    
    int top() const {
        return heap.empty() ? -1 : heap[0];
    }
    
    bool empty() const {
        return heap.empty();
    }
    
    void print() const {
        for (int val : heap)
            std::cout << val << " ";
        std::cout << std::endl;
    }
};

void demoSTLHeap() {
    std::cout << "\n=== STL Priority Queue ===" << std::endl;
    std::priority_queue<int, std::vector<int>, std::greater<int>> minHeap;
    minHeap.push(8);
    minHeap.push(3);
    minHeap.push(5);
    minHeap.push(1);
    
    std::cout << "Минимальный элемент: " << minHeap.top() << std::endl;
    minHeap.pop();
    std::cout << "Следующий минимальный: " << minHeap.top() << std::endl;
}

int main() {
    std::cout << "=== Бинарная куча на C++ ===" << std::endl;
    MinHeap heap;
    heap.push(8);
    heap.push(3);
    heap.push(5);
    heap.push(1);
    heap.push(6);
    
    std::cout << "Куча: ";
    heap.print();
    std::cout << "Минимальный элемент: " << heap.top() << std::endl;
    std::cout << "Извлечение: " << heap.pop() << std::endl;
    std::cout << "Куча после извлечения: ";
    heap.print();
    
    demoSTLHeap();
    return 0;
}




Хеш-таблица
#include <iostream>
#include <vector>
#include <list>
#include <string>
#include <functional>
#include <stdexcept>

template<typename K, typename V>
class HashMap {
private:
    std::vector<std::list<std::pair<K, V>>> table;
    size_t capacity;
    size_t count;
    double loadFactor;
    
    size_t hash(const K& key) const {
        return std::hash<K>{}(key) % capacity;
    }
    
    void resize() {
        size_t newCapacity = capacity * 2;
        std::vector<std::list<std::pair<K, V>>> newTable(newCapacity);
        
        for (const auto& bucket : table) {
            for (const auto& pair : bucket) {
                size_t newIndex = std::hash<K>{}(pair.first) % newCapacity;
                newTable[newIndex].push_back(pair);
            }
        }
        
        table = std::move(newTable);
        capacity = newCapacity;
    }

public:
    HashMap(size_t cap = 10, double lf = 0.75) : capacity(cap), count(0), loadFactor(lf) {
        table.resize(capacity);
    }
    
    void put(const K& key, const V& value) {
        if (static_cast<double>(count) / capacity >= loadFactor) {
            resize();
        }
        
        size_t index = hash(key);
        auto& bucket = table[index];
        
        // Проверяем, есть ли уже ключ
        for (auto& pair : bucket) {
            if (pair.first == key) {
                pair.second = value;
                return;
            }
        }
        
        // Добавляем новую пару
        bucket.emplace_back(key, value);
        count++;
    }
    
    V get(const K& key) const {
        size_t index = hash(key);
        const auto& bucket = table[index];
        
        for (const auto& pair : bucket) {
            if (pair.first == key) {
                return pair.second;
            }
        }
        
        throw std::out_of_range("Key not found");
    }
    
    void remove(const K& key) {
        size_t index = hash(key);
        auto& bucket = table[index];
        
        for (auto it = bucket.begin(); it != bucket.end(); ++it) {
            if (it->first == key) {
                bucket.erase(it);
                count--;
                return;
            }
        }
        
        throw std::out_of_range("Key not found");
    }
    
    bool contains(const K& key) const {
        try {
            get(key);
            return true;
        } catch (const std::out_of_range&) {
            return false;
        }
    }
    
    size_t size() const {
        return count;
    }
    
    void print() const {
        for (size_t i = 0; i < capacity; i++) {
            if (!table[i].empty()) {
                std::cout << "Bucket " << i << ": ";
                for (const auto& pair : table[i]) {
                    std::cout << "(" << pair.first << ", " << pair.second << ") ";
                }
                std::cout << std::endl;
            }
        }
    }
};

int main() {
    std::cout << "\n=== Хеш-таблица на C++ ===" << std::endl;
    
    HashMap<std::string, int> map;
    
    // Добавление элементов
    map.put("apple", 5);
    map.put("banana", 3);
    map.put("orange", 7);
    map.put("grape", 2);
    map.put("kiwi", 8);
    
    std::cout << "Хеш-таблица после добавления:" << std::endl;
    map.print();
    std::cout << "Количество элементов: " << map.size() << std::endl;
    
    // Поиск элементов
    std::cout << "\nЗначение для 'apple': " << map.get("apple") << std::endl;
    std::cout << "Содержит 'banana': " << (map.contains("banana") ? "true" : "false") << std::endl;
    
    // Обновление значения
    map.put("apple", 10);
    std::cout << "Обновлённое значение для 'apple': " << map.get("apple") << std::endl;
    
    // Удаление элемента
    map.remove("banana");
    std::cout << "\nПосле удаления 'banana', количество элементов: " << map.size() << std::endl;
    std::cout << "Содержит 'banana': " << (map.contains("banana") ? "true" : "false") << std::endl;
    
    return 0;
}
