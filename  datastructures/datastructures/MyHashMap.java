package datastructures;

public class MyHashMap<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int SIZE = 32; // Small size to keep things simple
    private Entry<K, V>[] table;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        table = new Entry[SIZE];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % SIZE;
    }

    public void put(K key, V value) {
        int index = hash(key);
        Entry<K, V> node = table[index];

        if (node == null) {
            table[index] = new Entry<>(key, value);
            return;
        }

        Entry<K, V> prev = null;
        while (node != null) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
            prev = node;
            node = node.next;
        }

        prev.next = new Entry<>(key, value);
    }

    public V get(K key) {
        int index = hash(key);
        Entry<K, V> node = table[index];

        while (node != null) {
            if (node.key.equals(key)) return node.value;
            node = node.next;
        }

        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public V getOrDefault(K key, V defaultValue) {
        V val = get(key);
        return val != null ? val : defaultValue;
    }

    public String[] keySet() {
        // Max 100 keys for simplicity
        String[] keys = new String[100];
        int idx = 0;

        for (int i = 0; i < SIZE; i++) {
            Entry<K, V> node = table[i];
            while (node != null) {
                keys[idx++] = node.key.toString();
                node = node.next;
            }
        }

        String[] result = new String[idx];
        System.arraycopy(keys, 0, result, 0, idx);
        return result;
    }
}
