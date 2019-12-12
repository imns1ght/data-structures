
#ifndef LIST_H
#define LIST_H

#include <initializer_list>  // std::initializer_list
#include <iostream>          // std::cout, std::endl
#include <iterator>
#include <vector>

namespace sc {

template <typename T>
class List {
        using size_type = unsigned long;            //!< The size type.
        using value_type = T;                       //!< The value type.
        using pointer = value_type*;                //!< Pointer to a value stored in the container.
        using reference = value_type&;              //!< Reference to a value stored in the container.
        using const_reference = const value_type&;  //!< Const reference to a value stored in the container.

       private:
        struct Node {
                T data;      //<! Data field
                Node* prev;  //<! Pointer to the previous node in the list.
                Node* next;  //<! Pointer to the next node in the list.

                //<!Basic constructor.
                Node(const T& d = T(), Node* p = nullptr, Node* n = nullptr) : data(d), prev(p), next(n) {}
        };

       public:
        class const_iterator {
               public:
                // Below we have the iterator_traits common interface
                /// Difference type used to calculated distance between iterators.
                typedef std::ptrdiff_t difference_type;
                typedef T value_type;  //!< Value type the iterator points to.
                typedef T* pointer;    //!< Pointer to the value type.
                typedef T& reference;  //!< Reference to the value type.
                typedef std::bidirectional_iterator_tag iterator_category;  //!< Iterator category.

                const_iterator() : current(nullptr) {}

                const T& operator*() const { return current->data; }

                //++it;
                const_iterator& operator++() {
                        current = current->next;
                        return current;
                }

                // it++;
                const_iterator operator++(int) {
                        auto old = *this;
                        current = current->next;
                        return old;
                }

                //--it;
                const_iterator& operator--() {
                        current = current->prev;
                        return current;
                }

                // it--;
                const_iterator operator--(int) {
                        auto old = *this;
                        current = current->prev;
                        return old;
                }

                bool operator==(const const_iterator& rhs) const { return (current == rhs.current); }

                bool operator!=(const const_iterator& rhs) const { return (current != rhs.current); }

               protected:
                Node* current;
                const_iterator(Node* p) : current(p) {}
                friend class List<T>;
        };

        class iterator : public const_iterator {
               public:
                // Below we have the iterator_traits common interface
                /// Difference type used to calculated distance between iterators.
                typedef std::ptrdiff_t difference_type;
                typedef T value_type;  //!< Value type the iterator points to.
                typedef T* pointer;    //!< Pointer to the value type.
                typedef T& reference;  //!< Reference to the value type.
                typedef std::bidirectional_iterator_tag iterator_category;  //!< Iterator category.

                iterator() { /*Empty*/
                }

                const T& operator*() const { return current->data; }

                T& operator*() { return current->data; }

                //++it;
                iterator operator++() {
                        current = current->next;
                        return current;
                }

                // it++;
                iterator operator++(int) {
                        iterator old = *this;
                        current = current->next;
                        return old;
                }

                //--it;
                iterator operator--() {
                        current = current->prev;
                        return current;
                }

                // it--;
                iterator operator--(int) {
                        iterator old = *this;
                        current = current->prev;
                        return old;
                }

                bool operator==(const iterator& rhs) const { return (current == rhs.current); }

                bool operator!=(const iterator& rhs) const { return (current != rhs.current); }

               protected:
                Node* current;
                iterator(Node* p) : current(p) {}
                friend class List<T>;
        };

        //[I]SPECIALMEMBERS
        //! @brief default constructor that creates an empty linked list.
        List() {}

        //! @brief desconstructor.
        ~List() {
                clear();  // Clears the contents of the linked list.
        }

        //! @brief constructs the linked list with count default-inserted instances of T.
        /*@param count Size of the list for constrution.
         */
        explicit List(int count) {
                for (int i = 0; i < count; i++) {
                        push_back(0);
                }
        }

        //! @brief constructs the list with the contents of the range [first, last).
        /*@param first Iterator for the first element for insertion.
         *@param last Iterator for the last element for insertion.
         */
        template <typename InputIt>
        List(InputIt first, InputIt last) {
                while (first != last) {
                        push_back(*first++);
                }

                push_back(*first++);
        }

        //! @brief constructs the list with the deep copy of the contents of other.
        /*@param other List for copy.
         */
        List(const List& other) {
                auto it = other.begin();

                while (it != nullptr) {
                        push_back(*it++);
                }
        }

        //! @brief constructs the list with the contents of the initializer list init.
        /*@param ilist Initializer list for copy.
         */
        List(std::initializer_list<T> ilist) {
                for (auto& e : ilist) {
                        push_back(e);
                }
        }

        //! @brief assign operator.
        /*@param other Reference to the value for assigment.
         */
        List& operator=(const List& other) {
                clear();

                auto it = other.begin();

                while (it != nullptr) {
                        push_back(*it++);
                }

                return *this;
        }

        //! @brief assign operator.
        /*@param other Reference to the value for assigment.
         */
        List& operator=(List&& other) {
                // Assign
                this->m_size = other.m_size;
                this->m_head = other.m_head;
                this->m_tail = other.m_tail;

                // Invalidate the 'other'
                other.m_size = 0;
                other.m_head = nullptr;
                other.m_tail = nullptr;

                return *this;
        }

        //! @brief list Assign.
        /*@param ilist Reference to the initializer list for assigment.
         */
        List& operator=(std::initializer_list<T> ilist) {
                clear();

                for (auto& e : ilist) {
                        push_back(e);
                }

                return *this;
        }

        //! @brief Check if lists are equal.
        /*@param rhs Reference to the list for comparison.
         *@return True if lists are equal, false otherwise.
         */
        bool operator==(const List<T>& rhs) {
                auto it_head = this->m_head;  // Node to the begin of lhs.

                if (this->m_size != rhs.size()) { // Check if lhr and rhs are the same size to
                        return false;             // to avoid unnecessary comparison.
                }

                for (auto i = rhs.begin(); i != nullptr && it_head != nullptr; i++) {
                        if (*i != it_head->data) {      // Comparisons
                                return false;
                        }

                        it_head = it_head->next;
                }

                return true;
        }

        //! @brief Check if lists are different.
        /*@param rhs Reference to the list for comparison.
         *@return True if lists are different, false otherwise.
         */
        bool operator!=(const List<T>& rhs) {
                auto it_head = this->m_head;  // Node to the begin of lhs.

                if (this->m_size != rhs.size()) { // Check if lhr and rhs are the same size to
                        return true;              // to avoid unnecessary comparison.
                }

                for (auto i = rhs.begin(); i != nullptr && it_head != nullptr; i++) {
                        if (*i != it_head->data) {
                                return true;
                        }

                        it_head = it_head->next;
                }

                return false;
        }

        //[II]ITERATORS

        /*!@brief Returns iterator to the beginning.
         * @return Iterator to the first element.
         */
        iterator begin() { return iterator(m_head); }

        /*!@brief Returns a const_iterator to the beginning.
         *@return Const_iterator to the first element.
         */
        const_iterator begin() const { return const_iterator(m_head); }

        /*!@brief Returns iterator to the past-end.
         *@return Iterator to the past-end.
         */
        iterator end() { return iterator(m_tail); }

        /*!@brief Returns a const_iterator to the past-end.
         *@return Iterator to the past-end.
         */
        const_iterator end() const { return const_iterator(m_tail); }

        //[III] Capacity
        //! @brief returns the number of elements.
        int size() const { return m_size; }

        //! @brief checks whether the container is empty.
        bool empty() const { return m_size == 0; }

        //[IV-a] Modifiers
        //! @brief clears the contents.
        void clear() {
                // Process nodes until the end of the linked list.
                while (m_head != nullptr) {
                        auto target = m_head;   // Node to remove.
                        m_head = m_head->next;  // Advances to the next node.
                        delete target;          // Remove the target node.
                }
                m_size = 0;
        }

        /*!@brief Returns content of the beginning.
         *@return First element.
         */
        T& front() { return m_head->data; }

        /*!@brief Returns content of the beginning.
         *@return First element.
         */
        const T& front() const { return m_head->data; }

        /*!@brief Returns content of the end.
         *@return Last element.
         */
        T& back() { return m_tail->data; }

        /*!@brief Returns content of the end.
         *@return Last element.
         */
        const T& back() const { return m_tail->data; }

        /*!@brief Insert value at front.
         *@param value Element for insertion.
         */
        void push_front(const T& value) {
                if (m_head == nullptr) {
                        m_head = new Node;
                        m_tail = new Node;
                        m_head->data = value;
                        m_head->next = m_tail;
                        m_tail->prev = m_head;
                } else {
                        //! Create a new node with value and
                        //! with next to current head.
                        Node* old = m_head;
                        m_head = new Node;  //!< New head.
                        m_head->data = value;
                        m_head->next = old;
                }

                m_size++;
        }

        /*!@brief Insert value at end.
         *@param value Element for insertion.
         */
        void push_back(const T& value) {
                // Special case: empty list.
                if (m_head == nullptr) {
                        m_head = new Node;
                        m_head->data = value;
                        m_tail = m_head;

                } else {
                        // Create a new node and define the tail->next to it.
                        Node* old = m_tail;
                        m_tail = new Node;
                        m_tail->data = value;
                        m_tail->prev = old;
                        old->next = m_tail;
                }

                m_size++;
        }

        //!@brief Remove value at front.
        void pop_front() {
                //! Special case: empty list.
                if (m_head == nullptr) {
                        return;
                }

                Node* tmp = m_head;  // Store the old head

                //! Special case: one element in the list.
                if (m_head == m_tail) {
                        m_head = m_tail = nullptr;
                } else {
                        m_head = m_head->next;  // Update to the new head.
                        m_head->prev = nullptr;
                }
                m_size--;    // Decrement the size.
                delete tmp;  // Delete the old head.
        }

        //!@brief Remove value at end.
        void pop_back() {
                //! Special case: empty list.
                if (m_head == nullptr) {
                        return;
                }

                Node* tmp = m_tail;  // Store the old tail.

                //! Special case: one element in the list.
                if (m_head == m_tail) {
                        m_tail = m_head = nullptr;
                } else {
                        m_tail = m_tail->prev;  // Update to the new tail.
                        m_tail->next = nullptr;
                }

                m_size--;    // Decrement the size.
                delete tmp;  // Delete the old tail.
        }

        //[IV-b] Modifiers with iterators
        /*!@brief Assign value count times in the array.
         *@param count Times of assignment
         *@param value Value to be assigned
         */
        void assign(int count, const T& value) {
                clear();

                for (auto i{0}; i < count; i++) {
                        push_back(value);
                }
        }

        template <class InItr>
        /*!@brief Assign values from [first, last)
         *@param first First element of array
         *@param last Element after the last element of array
         */
        void assign(InItr first, InItr last) {
                clear();

                while (first != last) {
                        push_back(*first++);
                }

                push_back(*first++);
        }

        /*!@brief Assigns list to the vector.
         *@param ilist List for assignment.
         */
        void assign(std::initializer_list<T> ilist) {
                clear();

                for (auto& e : ilist) {
                        push_back(e);
                }
        }

        /*!@brief Inserts value at position pos.
         *@param pos Position for insertion
         *@param value Number for insertion.
         */
        iterator insert(iterator pos, const T& value) {
                Node* new_node = new Node;
                new_node->data = value;
                Node* current = pos.current;

                if (pos == begin()) {
                        push_front(value);
                } else if (pos == end()) {
                        push_back(value);
                } else {
                        new_node->prev = current->prev;
                        new_node->next = current;
                        current->prev = new_node;
                        m_size++;
                }

                return iterator(new_node);
        }

        /*!@brief Inserts value at position pos.
         *@param pos Position for insertion
         *@param value Number for insertion.
         */
        iterator insert(const_iterator pos, const T& value) {
                Node* new_node = new Node;
                new_node->data = value;
                Node* current = pos.current;

                if (pos == begin()) {
                        push_front(value);
                } else if (pos == end()) {
                        push_back(value);
                } else {
                        new_node->prev = current->prev;
                        new_node->next = current;
                        current->prev = new_node;
                        m_size++;
                }

                return iterator(new_node);
        }

        template <typename InItr>
        iterator insert(iterator pos, InItr first, InItr last) {
                //TODO
        }

        template <typename InItr>
        iterator insert(const_iterator pos, InItr first, InItr last) {
                //TODO
        }

        iterator insert(const_iterator pos, std::initializer_list<T> ilist) {
                // #TODO

                // for (auto i = ilist.end() - 1; i != ilist.begin() - 1; i--) {
                //         pos = insert(pos, *i);
                // }

                // return iterator(pos.current);
        }

        /*!@brief Removes the object at position pos.
         *@param pos Position to erase.
         *@return Iterator to the element that follows pos.
         */
        iterator erase(iterator itr) {
                Node* current = itr.current;
                Node* prev = current->prev;
                Node* next = current->next;

                if (itr == begin()) {
                        pop_front();
                } else if (itr == end()) {
                        pop_back();
                        return iterator(prev);
                } else {
                        prev->next = next;
                        next->prev = prev;
                        delete current;
                        m_size--;
                }

                return iterator(next);
        }

        /*!@brief Removes the object at position pos.
         *@param pos Position to erase.
         *@return Iterator to the element that follows pos.
         */
        iterator erase(const_iterator itr) {
                Node* current = itr.current;
                Node* prev = current->prev;
                Node* next = current->next;

                if (itr == begin()) {
                        pop_front();
                } else if (itr == end()) {
                        pop_back();
                        return iterator(prev);
                } else {
                        prev->next = next;
                        next->prev = prev;
                        delete current;
                        m_size--;
                }

                return iterator(next);
        }

        iterator erase(iterator first, iterator last) {
                // #TODO
                // iterator itr = first;

                // while (itr != last) {
                //         itr = erase(itr);
                // }

                // return itr;
        }

        iterator erase(const_iterator first, const_iterator last) {
                // #TODO
        }

        const_iterator find(const T& value) const {
                Node* tmp;

                for (tmp = m_head; tmp != nullptr && !(tmp->data == value); tmp = tmp->next) {
                }

                return (tmp != nullptr);
        }

        void print() const {
                Node* tmp = m_head;

                std::cerr << "[";
                while (tmp != nullptr) {
                        std::cerr << tmp->data << " ";
                        tmp = tmp->next;
                }
                std::cerr << "]\n";
        }

       private:
        int m_size = 0;
        Node* m_head = nullptr;
        Node* m_tail = nullptr;
};

}  // namespace sc

#endif