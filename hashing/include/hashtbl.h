#ifndef HASHING_H
#define HASHING_H

#include <algorithm>     // std::find.
#include <cmath>         // std::sqrt.
#include <forward_list>  // std::forward_list.
#include <functional>    // std::hash, std::equal_to.
#include <iostream>      // std::cout.
#include <stdexcept>     // std::out_of_range
#include <vector>        // std::vector.

namespace ac {

template <class KeyType, class DataType>
class HashEntry {
       public:
        HashEntry(KeyType k_, DataType d_) : m_key(k_), m_data(d_) {}

        KeyType m_key;    //!< Key associated to data.
        DataType m_data;  //!< Data associated to key.
};

template <typename KeyType, typename DataType, typename KeyHash = std::hash<KeyType>,
          typename KeyEqual = std::equal_to<KeyType>>
class HashTbl {
       public:
        using Entry = HashEntry<KeyType, DataType>;  //!< Alias to the data stored in hash table.

        //! \brief Constructs the hash table with a specific size.
        //! \param tbl_size_ Size of the hash table.
        HashTbl(size_t tbl_size_ = DEFAULT_SIZE) : m_size{tbl_size_}, m_count{0} {
                m_main_table.resize(tbl_size_);
        }

        //! \brief Destructs the hash table.
        virtual ~HashTbl() {}

        //! \brief Constructs a copy of a hash table given by argument.
        //! \param other Hash table to copy.
        HashTbl(const HashTbl& other);

        //! \brief Constructs the hash table with values from a std::initializer_list.
        //! \param ilist List of elements.
        HashTbl(std::initializer_list<Entry> ilist);

        //! \brief Assigns values from a hash table.
        //! \param other Hash table to copy.
        //! \return Object itself.
        HashTbl& operator=(const HashTbl& other) {
                m_main_table = other.m_main_table;
                m_size = other.m_size;
                m_count = other.m_count;

                return *this;
        }

        //! \brief Assigns values from a std::initializer_list.
        //! \param ilist List of elements.
        //! \return Object itself.
        HashTbl& operator=(std::initializer_list<Entry> ilist) {
                m_size = ilist.size();
                m_main_table.resize(m_size);

                // Initialize the main list from ilist.
                size_t n_values = 0;
                for (auto& value : ilist) {
                        this->insert(value.m_key, value.m_data);
                        n_values++;
                }
                m_count = n_values;

                return *this;
        }

        //! \brief Access or insert element associated to a key.
        //! \param key_ Key associated to data.
        //! \return Data associated to the key.
        DataType& operator[](const KeyType& key_);

        //! \brief Inserts data associated to a key.
        //! \param key_ Key associated to data.
        //! \param data_item_ Data to insert.
        //! \return True if not collision ocurred, false otherwise.
        bool insert(const KeyType& key_, const DataType& data_item_);

        //! \brief Erases data associated to a key.
        //! \param key_ Key associated to data.
        //! \param data_item_ Data to insert.
        //! \return True if erased, false otherwise.
        bool erase(const KeyType& key_);

        //! \brief Retrieves data associated to a key.
        //! \param key_ Key associated to data.
        //! \param data_item_ Store the data associated to key.
        //! \return True if retrieved, false otherwise.
        bool retrieve(const KeyType& key_, DataType& data_item_) const;

        //! \brief Clears the contents.
        inline void clear(void) {
                m_main_table.clear();
                m_size = m_count = 0;
        }

        //! \brief Checks whether the hash table is empty
        //! \return True if empty, false otherwise.
        inline bool empty(void) const { return m_count == 0; }

        //! \brief Returns the number of elements
        //! \return Number of elements.
        inline size_t size(void) const { return m_count; }

        //! \brief Access element associated to a key.
        //! \param key_ Key associated to data.
        //! \throw std::out_of_range
        //! \return Data associated to the key.
        DataType& at(const KeyType& key_);

        //! \brief Returns the number of elements matching specific key.
        //! \param key_ Key associated to data.
        //! \return Number of elements.
        size_t count(const KeyType& key_) const;

        //! \brief Extractor operator.
        friend std::ostream& operator<<(std::ostream& os, const HashTbl& table) {
                for (size_t i = 0; i < table.m_main_table.size(); i++) {
                        os << "[" << i << "]\n";

                        for (auto it = table.m_main_table[i].begin();
                             it != table.m_main_table[i].end(); it++) {
                                os << ">>>" << it->m_data << "\n\n";
                        }
                        os << std::endl;
                }

                return os;
        }

       private:
        static const short DEFAULT_SIZE = 11;  //!< Hash table’s default size.
        const float DEFAULT_LOAD_FACTOR = 1;   //!< Hash table’s default load factor.
        size_t m_size;                         //!< Hash table size.
        size_t m_count;                        //!< Number of elements in the table.
        std::vector<std::forward_list<Entry>> m_main_table;  //!< Hash table.

        // \brief Checks if a number is prime.
        // \param n Number to check.
        // \return True if a prime number, false otherwise.
        bool is_prime(size_t n);

        // \brief Checks the next number prime >= n.
        // \param n Number to check.
        // \return Next prime.
        size_t next_prime(size_t n);

        // \brief Returns the load factor.
        // \return Load factor.
        inline float load_factor(void) { return m_count / m_size; }

        // \brief Change Hash table size if load factor λ > 1.0.
        void rehash(void);
};

template <class KeyType, class DataType, class KeyHash, class KeyEqual>
HashTbl<KeyType, DataType, KeyHash, KeyEqual>::HashTbl(const HashTbl& other) {
        m_main_table = other.m_main_table;
        m_size = other.m_size;
        m_count = other.m_count;
}

template <class KeyType, class DataType, class KeyHash, class KeyEqual>
HashTbl<KeyType, DataType, KeyHash, KeyEqual>::HashTbl(std::initializer_list<Entry> ilist) {
        m_size = ilist.size();
        m_main_table.resize(m_size);

        // Initialize the main list from ilist.
        size_t n_values = 0;
        for (auto& value : ilist) {
                this->insert(value.m_key, value.m_data);
                n_values++;
        }
        m_count = n_values;
}

template <class KeyType, class DataType, class KeyHash, class KeyEqual>
DataType& HashTbl<KeyType, DataType, KeyHash, KeyEqual>::operator[](const KeyType& key_) {
        KeyHash hashFunc;                    // Instantiate the "functor" for primary hash.
        KeyEqual equalFunc;                  // Instantiate the "functor" for the equal to test.
        auto addr(hashFunc(key_) % m_size);  // Apply double hashing method.

        // Return the reference to the data associated to the key.
        for (auto it = m_main_table[addr].begin(); it != m_main_table[addr].end(); it++) {
                //  Comparing keys inside the collision list.
                if (true == equalFunc((*it).m_key, key_)) {
                        return (*it).m_data;
                }
        }
        m_count++;  // Update the number of elements in the list.

        // Create new entry if not located.
        Entry new_entry(key_, DataType());
        m_main_table[addr].push_front(new_entry);

        // Used to return the reference of the new entry.
        auto& ref_new_entry = m_main_table[addr].front();

        return ref_new_entry.m_data;
}

template <class KeyType, class DataType, class KeyHash, class KeyEqual>
bool HashTbl<KeyType, DataType, KeyHash, KeyEqual>::insert(const KeyType& key_,
                                                           const DataType& data_item_) {
        KeyHash hashFunc;                   // Instantiate the "functor" for primary hash.
        KeyEqual equalFunc;                 // Instantiate the "functor" for the equal to test.
        Entry new_entry(key_, data_item_);  // Create a new entry based on arguments.

        auto addr(hashFunc(key_) % m_size);  // Apply double hashing method.

        for (auto it = m_main_table[addr].begin(); it != m_main_table[addr].end(); it++) {
                //  Comparing keys inside the collision list.
                if (true == equalFunc((*it).m_key, new_entry.m_key)) {
                        (*it).m_data = new_entry.m_data;
                        return false;
                }
        }

        m_main_table[addr].push_front(new_entry);
        m_count++;

        if (load_factor() >= DEFAULT_LOAD_FACTOR) {
                rehash();
        }

        return true;
}

template <class KeyType, class DataType, class KeyHash, class KeyEqual>
bool HashTbl<KeyType, DataType, KeyHash, KeyEqual>::erase(const KeyType& key_) {
        KeyHash hashFunc;                    // Instantiate the "functor" for primary hash.
        KeyEqual equalFunc;                  // Instantiate the "functor" for the equal to test.
        auto addr(hashFunc(key_) % m_size);  // Apply double hashing method.

        auto it = m_main_table[addr].begin();
        auto before_it = m_main_table[addr].before_begin();
        for (; it != m_main_table[addr].end(); it++, before_it++) {
                //  Comparing keys inside the collision list.
                if (true == equalFunc((*it).m_key, key_)) {
                        m_main_table[addr].erase_after(before_it);
                        m_count--;
                        return true;
                }
        }

        return false;
}

template <class KeyType, class DataType, class KeyHash, class KeyEqual>
DataType& HashTbl<KeyType, DataType, KeyHash, KeyEqual>::at(const KeyType& key_) {
        KeyHash hashFunc;                    // Instantiate the "functor" for primary hash.
        KeyEqual equalFunc;                  // Instantiate the "functor" for the equal to test.
        auto addr(hashFunc(key_) % m_size);  // Apply double hashing method.

        // Return the reference to the data associated to the key.
        for (auto it = m_main_table[addr].begin(); it != m_main_table[addr].end(); it++) {
                //  Comparing keys inside the collision list.
                if (true == equalFunc((*it).m_key, key_)) {
                        return (*it).m_data;
                }
        }

        throw std::out_of_range("error: index is out of range!\n");
}

template <class KeyType, class DataType, class KeyHash, class KeyEqual>
size_t HashTbl<KeyType, DataType, KeyHash, KeyEqual>::count(const KeyType& key_) const {
        KeyHash hashFunc;                    // Instantiate the "functor" for primary hash.
        size_t n_count = 0;                  // Number of elements in colision list.
        auto addr(hashFunc(key_) % m_size);  // Apply double hashing method.

        for (auto it = m_main_table[addr].begin(); it != m_main_table[addr].end(); it++) {
                n_count++;
        }

        return n_count;
}

template <class KeyType, class DataType, class KeyHash, class KeyEqual>
bool HashTbl<KeyType, DataType, KeyHash, KeyEqual>::retrieve(const KeyType& key_,
                                                             DataType& data_item_) const {
        KeyHash hashFunc;                    // Instantiate the "functor" for primary hash.
        KeyEqual equalFunc;                  // Instantiate the "functor" for the equal to test.
        auto addr(hashFunc(key_) % m_size);  // Apply double hashing method.

        for (auto it = m_main_table[addr].begin(); it != m_main_table[addr].end(); it++) {
                //  Comparing keys inside the collision list.
                if (true == equalFunc((*it).m_key, key_)) {
                        data_item_ = (*it).m_data;
                        return true;
                }
        }

        return false;
}

template <class KeyType, class DataType, class KeyHash, class KeyEqual>
bool HashTbl<KeyType, DataType, KeyHash, KeyEqual>::is_prime(size_t n) {
        size_t i;

        if (n <= 1) return false;

        for (i = 2; i <= std::sqrt(n); i++) {
                if (n % i == 0) {
                        return false;
                }
        }

        return true;
}

template <class KeyType, class DataType, class KeyHash, class KeyEqual>
size_t HashTbl<KeyType, DataType, KeyHash, KeyEqual>::next_prime(size_t n) {
        while (!is_prime(n)) {
                n++;
        }

        return n;
}

template <class KeyType, class DataType, class KeyHash, class KeyEqual>
void HashTbl<KeyType, DataType, KeyHash, KeyEqual>::rehash() {
        HashTbl new_table(next_prime(this->m_size * 2));
        KeyHash hashFunc;  // Instantiate the "functor" for primary hash.

        for (size_t i = 0; i < m_size; i++) {
                for (auto j = m_main_table[i].begin(); j != m_main_table[i].end(); j++) {
                        auto addr(hashFunc(j->m_key) % new_table.m_size);
                        new_table.m_main_table[addr].push_front(*j);
                        new_table.m_count++;
                }
        }

        *this = new_table;
}

}  // namespace ac

#endif