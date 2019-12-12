/*!
 * \file vector.h
 * \author Jefferson Bruno, Ranieri Santos
 * \date 21/05/2019
 * \n https://github.com/imns1ght/vector
 */

#ifndef VECTOR_H
#define VECTOR_H

#include <algorithm>         // std::copy, ...
#include <cassert>           // assert
#include <initializer_list>  // std::initializer_list
#include <iostream>          // std::cout, std::endl, ...
#include <iterator>          // std::ostream_iterator
#include <string>            // memmove()
#include "iterator.h"        // iterator of the vector

namespace alt {

template <typename T>
class vector {
       public:
        // Container common interface.
        typedef T value_type;              //!< The value type.
        typedef T& reference;              //!< Reference to a value stored in the container.
        typedef const T& const_reference;  //!< Const reference to a value stored in the container.
        typedef T* pointer;                //!< Pointer to a value stored in the container.
        typedef vec_iterator<T> iterator;  //!< Iterator.
        typedef vec_iterator<const T> const_iterator;  //!< Const iterator.
        typedef unsigned long size_type;               //!< The size type.

        ///////////////////////////////////////////////////////////////////////////////
        // Member functions
        ///////////////////////////////////////////////////////////////////////////////

        //! \brief Default constructor: constructs an empty container, with no elements.
        vector();

        //! \brief Constructs an container with count default-inserted instances.
        //! \param count Number of instances.
        explicit vector(size_type count);

        //! \brief Constructs the container with the contents of the range [first, last).
        //! \param first Iterator for the first element for insertion.
        //! \param last Iterator for the last element for insertion.
        template <typename InputIt>
        vector(InputIt first, InputIt last);

        //! \brief Constructs the container with the deep copy of the contents of other.
        //! \param other Container for copy.
        vector(const vector& other);

        //! \brief Constructs the container with the contents of the initializer list.
        //! \param ilist Initializer list for copy.
        vector(std::initializer_list<value_type> ilist);

        //! \brief Desconstructor.
        virtual ~vector(void);

        //! \brief Copy assignment: copies all the elements from other into the container.
        //! \param other Vector object of the same type.
        vector& operator=(const vector& other);

        //! \brief Move assignment: moves the elements of other into the container.
        //! \param other Vector object of the same type.
        vector& operator=(vector&& other);

        //! \brief initializer list assignment: copies the elements of il into the container.
        //! \param ilist initializer_list object to copy content.
        vector& operator=(std::initializer_list<value_type> ilist);

        ///////////////////////////////////////////////////////////////////////////////
        // Compare functions
        ///////////////////////////////////////////////////////////////////////////////

        //! \brief Checks if the contents of lhs and rhs are equal, that is, they have the same
        //! number of elements and each element in lhs compares equal with the element in rhs at the
        //! same position.
        //! \param lhs Container to compare with rhs.
        //! \param rhs Container to compare with lhs.
        //! \return True if the contents of the containers are equal, false otherwise.
        bool operator==(const vector& rhs) const;

        //! \brief Checks if the contents of lhs and rhs are equal, that is, they have the same
        //! number of elements and each element in lhs compares equal with the element in rhs at the
        //! same position.
        //! \param lhs Container to compare with rhs.
        //! \param rhs Container to compare with lhs.
        //! \return True if the contents of the containers are not equal, false otherwise.
        bool operator!=(const vector& rhs) const;

        ///////////////////////////////////////////////////////////////////////////////
        // Iterators
        ///////////////////////////////////////////////////////////////////////////////

        //! \brief Return iterator to the beginning.
        //! \return Iterator to the first element.
        iterator begin(void);

        //! \brief Returns iterator to the past-end.
        //! \return Iterator to the past-end.
        iterator end(void);

        //! \brief Returns a const_iterator to the beginning.
        //! \return Const_iterator to the first element.
        const_iterator cbegin(void) const;

        //! \brief Returns a const_iterator to the past-end.
        //! \return Iterator to the past-end.
        const_iterator cend(void) const;

        ///////////////////////////////////////////////////////////////////////////////
        // Capacity
        ///////////////////////////////////////////////////////////////////////////////

        //! \brief Returns the number of elements in the vector.
        //! \return The number of elements in the container.
        size_type size(void) const;

        //! \brief Returns the size of the storage space currently allocated for the vector.
        //! \return The size of the currently allocated storage capacity in the vector, measured in
        //! terms of the number elements it can hold.
        size_type capacity(void) const;

        //! \brief Returns whether the vector is empty
        //! \return True if the container size is 0, false otherwise.
        bool empty(void) const;

        //! \brief Requests that the vector capacity be at least enough to contain n elements.
        void reserve(size_type new_cap);

        //! \brief Requests the container to reduce its capacity to fit its size.
        void shrink_to_fit(void);

        ///////////////////////////////////////////////////////////////////////////////
        // Element access
        ///////////////////////////////////////////////////////////////////////////////

        //! \brief Returns a reference to the element at position n in the vector container.
        //! \return The element at the specified position in the vector.
        reference operator[](size_type pos);

        //! \brief Returns a const reference to the element at position n in the vector container.
        //! \return The element at the specified position in the vector.
        const_reference operator[](size_type pos) const;

        //! \brief Returns a reference to the element at position pos in the vector.
        //! \param pos Position of an element in the container.
        //! \return The element at the specified position in the container.
        reference at(size_type pos);

        //! \brief Returns a const reference to the element at position pos in the vector.
        //! \param pos Position of an element in the container.
        //! \return The element at the specified position in the container.
        const_reference at(size_type pos) const;

        //! \brief Returns a reference to the first element in the vector.
        //! \return A reference to the first element in the vector container.
        reference front(void);

        //! \brief Returns a const reference to the first element in the vector.
        //! \return A const reference to the first element in the vector container.
        const_reference front(void) const;

        //! \brief Returns a reference to the last element in the vector.
        //! \return A reference to the last element in the vector container.
        reference back(void);

        //! \brief Returns a const reference to the last element in the vector.
        //! \return A const reference to the last element in the vector container.
        const_reference back(void) const;

        ///////////////////////////////////////////////////////////////////////////////
        // Modifiers
        ///////////////////////////////////////////////////////////////////////////////

        //! \brief Assign value count times in the container.
        //! \param count Times of assignment.
        //! \param value Value to be assigned.
        void assign(size_type count, const_reference value);

        //! \brief Assign values from [first, last)
        //! \param first First element of array
        //! \param last Element after the last element of array
        template <typename InputItr>
        void assign(InputItr first, InputItr last);

        //! \brief Assign initializer list content to the container.
        //! \param ilist Initializer list for assignment.
        void assign(const std::initializer_list<value_type> ilist);

        //! \brief Insert value to the end of the container.
        //! \param value Value to be copied.
        void push_back(const_reference value);

        //! \brief Removes the last element of the container.
        void pop_back(void);

        //! \brief Inserts value at position pos.
        //! \param pos Position for insertion.
        //! \param value Value for insertion.
        //! \return Iterator pointing to the inserted value.
        iterator insert(iterator pos, const_reference value);

        //! \brief Inserts value at position pos.
        //! \param pos Position for insertion.
        //! \param value Value for insertion.
        //! \return Iterator pointing to the inserted value.
        iterator insert(const_iterator pos, const_reference value);

        //! \brief Inserts values from range at position pos.
        //! \param pos Position for insertion;
        //! \param first First element of the range.
        //! \param last Position after the last element of the range.
        //! \return Iterator pointing to the first element inserted,
        template <typename InputItr>
        iterator insert(iterator pos, InputItr first, InputItr last);

        //! \brief Inserts values from range at position pos.
        //! \param pos Position for insertion;
        //! \param first First element of the range.
        //! \param last Position after the last element of the range.
        //! \return Iterator pointing to the first element inserted,
        template <typename InputItr>
        iterator insert(const_iterator pos, InputItr first, InputItr last);

        //! \brief Inserts values from an initializer list at position pos.
        //! \param pos Position for insertion.
        //! \param ilist List of elements.
        //! \return Iterator pointing to the first element inserted.
        iterator insert(iterator pos, const std::initializer_list<value_type> ilist);

        //! \brief Inserts values from an initializer list at position pos.
        //! \param pos Position for insertion.
        //! \param ilist List of elements.
        //! \return Iterator pointing to the first element inserted.
        iterator insert(const_iterator pos, const std::initializer_list<value_type> ilist);

        //! \brief Removes the object at position pos.
        //! \param pos Position to erase.
        //! \return Iterator to the element that follows pos.
        iterator erase(iterator pos);

        //! \brief Removes the object at position pos.
        //! \param pos Position to erase.
        //! \return Iterator to the element that follows pos.
        iterator erase(const_iterator pos);

        //! \brief Removes the range [first, last).
        //! \param first Iterator for the first element of the range.
        //! \param last Iterator for the past-end element of the range.
        //! \return An iterator pointing to the new location of the
        //! element that followed the last element erased by the function call.
        iterator erase(iterator first, iterator last);

        //! \brief Removes the range [first, last).
        //! \param first Iterator for the first element of the range.
        //! \param last Iterator for the past-end element of the range.
        //! \return An iterator pointing to the new location of the
        //! element that followed the last element erased by the function call.
        iterator erase(const_iterator first, const_iterator last);

        //! \brief Removes all elements from the container (which are destroyed), leaving the
        //! container with a size of 0.
        void clear(void);

       private:
        pointer m_storage;     //!< Data storage area for the dynamic array.
        size_type m_size;      //!< Current list size (or index past-last valid element).
        size_type m_capacity;  //!< List’s storage capacity.
};

template <typename T>
vector<T>::vector() : m_storage{new T[0]}, m_size{0}, m_capacity{0} {};

template <typename T>
vector<T>::vector(typename vector<T>::size_type count)
    : m_storage{new T[count]}, m_size{0}, m_capacity{count} {};

template <typename T>
template <typename InputIt>
vector<T>::vector(InputIt first, InputIt last) {
        m_capacity = m_size = std::distance(first, last);  // Update size/capacity of the container.
        m_storage = new T[m_capacity];                     // Allocate the memory needed.
        std::copy(first, last, m_storage);                 // Copy elements to the container.
}

template <typename T>
vector<T>::vector(const vector& other) {
        // Update size/capacity of the container.
        m_size = other.m_size;
        m_capacity = other.m_capacity;

        m_storage = new T[m_capacity];  // Allocate the memory needed.

        // Copy elements to the container.
        std::copy(&other.m_storage[0], &other.m_storage[m_size], m_storage);
}

template <typename T>
vector<T>::vector(std::initializer_list<T> ilist) {
        m_capacity = m_size = ilist.size();  // Update size/capacity of the container.
        m_storage = new T[m_capacity];       // Allocate the memory needed.

        // Copy elements from ilist to the container.
        int i = 0;
        for (auto& value : ilist) {
                m_storage[i++] = value;
        }
}

template <typename T>
vector<T>::~vector(void) {
        delete[] m_storage;  // Deallocate the memory block.
}

template <typename T>
vector<T>& vector<T>::operator=(const vector& other) {
        // Update size/capacity of the container.
        m_size = other.m_size;
        m_capacity = other.m_capacity;

        delete[] m_storage;             // Delete old memory.
        m_storage = new T[m_capacity];  // Allocate the memory needed.

        // Copy elements to the container.
        std::copy(&other.m_storage[0], &other.m_storage[m_size], m_storage);

        return *this;
}

template <typename T>
vector<T>& vector<T>::operator=(vector&& other) {
        // Update size/capacity of the container.
        m_size = other.m_size;
        m_capacity = other.m_capacity;

        delete[] m_storage;                      // Delete old memory.
        m_storage = std::move(other.m_storage);  // Move elements to the container.

        // Update 'other' container.
        other.m_size = 0;
        other.m_capacity = 0;
        other.m_storage = nullptr;

        return *this;
}

template <typename T>
vector<T>& vector<T>::operator=(std::initializer_list<T> ilist) {
        m_capacity = m_size = ilist.size();  // Update size/capacity of the container.

        delete[] m_storage;             // Delete old memory.
        m_storage = new T[m_capacity];  // Allocate the memory needed.

        // Copy elements from ilist to the container.
        int i = 0;
        for (auto& value : ilist) {
                m_storage[i++] = value;
        }

        return *this;
}

template <typename T>
bool vector<T>::operator==(const vector& rhs) const {
        // First, check if the size is different.
        if (m_size != rhs.size()) return false;

        // Check if the corresponding values are equal.
        for (size_t i = 0; i < rhs.size(); i++) {
                if (m_storage[i] != rhs[i]) {
                        return false;
                }
        }

        return true;
}

template <typename T>
bool vector<T>::operator!=(const vector& rhs) const {
        // First, check if the size is different.
        if (m_size != rhs.size()) return true;

        // Check if the corresponding values are equal.
        for (size_t i = 0; i < rhs.size(); i++) {
                if (m_storage[i] != rhs[i]) {
                        return true;
                }
        }

        return false;
}

template <typename T>
typename vector<T>::iterator vector<T>::begin(void) {
        return typename vector<T>::iterator(&m_storage[0]);
}

template <typename T>
typename vector<T>::iterator vector<T>::end(void) {
        return typename vector<T>::iterator(&m_storage[m_size]);
}

template <typename T>
typename vector<T>::const_iterator vector<T>::cbegin(void) const {
        return typename vector<T>::const_iterator(&m_storage[0]);
}

template <typename T>
typename vector<T>::const_iterator vector<T>::cend(void) const {
        return typename vector<T>::const_iterator(&m_storage[m_size]);
}

template <typename T>
typename vector<T>::size_type vector<T>::size(void) const {
        return m_size;
}

template <typename T>
typename vector<T>::size_type vector<T>::capacity(void) const {
        return m_capacity;
}

template <typename T>
bool vector<T>::empty(void) const {
        return (m_size == 0);
}

template <typename T>
void vector<T>::reserve(typename vector<T>::size_type new_cap) {
        // If we need more space.
        if (new_cap > m_capacity) {
                T* new_storage = new T[new_cap];         // Initialize array with the needed memory.
                std::copy(begin(), end(), new_storage);  // Copy elements from old -> new.
                delete[] m_storage;                      // Deallocate the old array.

                m_capacity = new_cap;     // Update the capacity of the new array.
                m_storage = new_storage;  // Points to the new memory.
        }
}

template <typename T>
void vector<T>::shrink_to_fit(void) {
        // If we don't need more space.
        if (m_capacity > m_size) {
                T* new_storage = new T[m_size];          // Initialize array with the needed memory.
                std::copy(begin(), end(), new_storage);  // Copy elements from old -> new.
                delete[] m_storage;                      // Deallocate the old array.

                m_capacity = m_size;      // Update the capacity of the new array.
                m_storage = new_storage;  // Points to the new memory.
        }
}

template <typename T>
typename vector<T>::reference vector<T>::operator[](typename vector<T>::size_type pos) {
        return m_storage[pos];
}

template <typename T>
typename vector<T>::const_reference vector<T>::operator[](typename vector<T>::size_type pos) const {
        return m_storage[pos];
}

template <typename T>
typename vector<T>::reference vector<T>::at(typename vector<T>::size_type pos) {
        // Check if 'pos' is an invalid position.
        if (pos >= m_size) {
                throw std::out_of_range("out of range!");
        }

        return m_storage[pos];
}

template <typename T>
typename vector<T>::const_reference vector<T>::at(typename vector<T>::size_type pos) const {
        // Check if 'pos' is an invalid position.
        if (pos >= m_size) {
                throw std::out_of_range("out of range!");
        }

        return m_storage[pos];
}

template <typename T>
typename vector<T>::reference vector<T>::front(void) {
        return m_storage[0];
}
template <typename T>
typename vector<T>::const_reference vector<T>::front(void) const {
        return m_storage[0];
}

template <typename T>
typename vector<T>::reference vector<T>::back(void) {
        return m_storage[m_size - 1];
}

template <typename T>
typename vector<T>::const_reference vector<T>::back(void) const {
        return m_storage[m_size - 1];
}

template <typename T>
void vector<T>::assign(typename vector<T>::size_type count,
                       typename vector<T>::const_reference value) {
        reserve(count);  // Allocate more memory if needed.

        // Assign 'count' instances of 'value'.
        for (size_t i = 0; i < count; i++) {
                m_storage[i] = value;
        }

        m_size = count;  // Update container size.
}

template <typename T>
template <typename InputItr>
void vector<T>::assign(InputItr first, InputItr last) {
        size_t num_elements = std::distance(first, last);  // Number of elements in the range.
        reserve(num_elements);                             // Allocate more memory if needed.

        // Assign values from the range.
        for (size_t i = 0; i < num_elements; i++) {
                m_storage[i] = *first++;
        }

        m_size = num_elements;  // Update container size.
}

template <typename T>
void vector<T>::assign(const std::initializer_list<T> ilist) {
        size_t num_elements = ilist.size();  // Number of elements in the list.
        reserve(num_elements);               // Allocate more memory if needed.

        // Assign values from the list.
        size_t i = 0;
        for (auto& value : ilist) {
                m_storage[i++] = value;
        }

        m_size = num_elements;  // Update container size.
}

template <typename T>
void vector<T>::push_back(typename vector<T>::const_reference value) {
        // Verify if the array is full
        if (m_size == m_capacity) {
                reserve(m_capacity * 2);
        }
        m_storage[m_size++] = value;
}

template <typename T>
void vector<T>::pop_back(void) {
        --m_size;
        m_storage[m_size].~T();
};

template <typename T>
typename vector<T>::iterator vector<T>::insert(typename vector<T>::iterator pos,
                                               typename vector<T>::const_reference value) {
        bool is_valid = false;

        // Check if pos is valid.
        for (auto i = begin(); i != end() + 1; i++) {
                if (i == pos) {
                        is_valid = true;
                        break;
                }
        }

        if (!is_valid) {
                return nullptr;  // If not valid.
        }

        int size_first = std::distance(begin(), pos);  // Elements before pos.
        int size_second = std::distance(pos, end());   // Elements after pos.

        if (m_size >= m_capacity) {
                m_size = m_size + 1;  // Update to one more element if necessary (value).
        }

        T* tmp = new T[m_size * 2];  // New array with the capacity updated.

        std::copy_n(begin(), size_first, tmp);                  // Copy elements before pos.
        tmp[size_first] = value;                                // Copy value to pos.
        std::copy_n(pos, size_second, (tmp + size_first + 1));  // Copy elements after pos.

        delete[] m_storage;   // Delete old array.
        m_storage = tmp;      // Update array to "array with value".
        m_capacity = m_size;  // Update capacity

        return typename vector<T>::iterator(&m_storage[size_first]);
}

template <typename T>
typename vector<T>::iterator vector<T>::insert(typename vector<T>::const_iterator pos,
                                               typename vector<T>::const_reference value) {
        int size_first = std::distance(begin(), pos);  // Elements before pos.
        int size_second = std::distance(pos, end());   // Elements after pos.
        m_size = m_size + 1;                           // Update to one more element (value).

        T* tmp = new T[m_size * 2];  // New array with the capacity updated.

        std::copy_n(begin(), size_first, tmp);                  // Copy elements before pos.
        tmp[size_first] = value;                                // Copy value to pos.
        std::copy_n(pos, size_second, (tmp + size_first + 1));  // Copy elements after pos.

        delete[] m_storage;   // Delete old array.
        m_storage = tmp;      // Update array to "array with value".
        m_capacity = m_size;  // Update capacity

        return pos;
}

template <typename T>
template <typename InputItr>
typename vector<T>::iterator vector<T>::insert(typename vector<T>::iterator pos, InputItr first,
                                               InputItr last) {
        int size = std::distance(first, last);  // Number of elements.

        last--;  // Decrease to a position with element valid.
        for (int i = 0; i < size; i++, last--) {
                pos = insert(pos, *last);
        }

        return pos;
}

template <typename T>
template <typename InputItr>
typename vector<T>::iterator vector<T>::insert(typename vector<T>::const_iterator pos,
                                               InputItr first, InputItr last) {
        int size = std::distance(first, last);  // Number of elements.

        last--;  // Decrease to a position with element valid.
        for (int i = 0; i < size; i++, last--) {
                pos = insert(pos, *last);
        }

        return pos;
}

template <typename T>
typename vector<T>::iterator vector<T>::insert(typename vector<T>::iterator pos,
                                               const std::initializer_list<T> ilist) {
        for (auto i = ilist.end() - 1; i != ilist.begin() - 1; i--) {
                pos = insert(pos, *i);
        }

        return pos;
}

template <typename T>
typename vector<T>::iterator vector<T>::insert(typename vector<T>::const_iterator pos,
                                               const std::initializer_list<T> ilist) {
        for (auto i = ilist.end() - 1; i != ilist.begin() - 1; i--) {
                pos = insert(pos, *i);
        }

        return pos;
}

template <typename T>
typename vector<T>::iterator vector<T>::erase(typename vector<T>::iterator pos) {
        // 'pos' must be valid and dereferenceable.
        if (pos != end()) {
                m_size--;  // Update container size.

                // Move elements after pos.
                for (auto it = pos; it != end(); it++) {
                        *it = *(it + 1);
                }
        }

        return pos;
}

template <typename T>
typename vector<T>::iterator vector<T>::erase(typename vector<T>::const_iterator pos) {
        // 'pos' must be valid and dereferenceable.
        if (pos != end()) {
                m_size--;  // Update container size.

                // Move elements after pos.
                for (auto it = pos; it != end(); it++) {
                        *it = *(it + 1);
                }
        }

        return pos;
}

template <typename T>
typename vector<T>::iterator vector<T>::erase(typename vector<T>::iterator first,
                                              typename vector<T>::iterator last) {
        // Delete elements of the range.
        for (int i = 0; i < std::distance(first, last); i++) {
                first = erase(first);
        }

        return first;
}

template <typename T>
typename vector<T>::iterator vector<T>::erase(typename vector<T>::const_iterator first,
                                              typename vector<T>::const_iterator last) {
        // Delete elements of the range.
        for (int i = 0; i < std::distance(first, last); i++) {
                first = erase(first);
        }

        return first;
}

template <typename T>
void vector<T>::clear(void) {
        for (size_t i = 0; i < m_capacity; i++) m_storage[i].~T();  // Destroy elements
        m_size = 0;                                                 // Update the size.
}

}  // namespace alt

#endif