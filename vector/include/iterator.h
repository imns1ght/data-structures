template <typename T>
class vec_iterator {
       public:
        // iterator_traits common interface.
        typedef T value_type;                    //!< Value type the vec_iterator points to.
        typedef T* pointer;                      //!< Pointer to the value type.
        typedef T& reference;                    //!< Reference to the value type.
        typedef std::ptrdiff_t difference_type;  //!< Used to calculated distance between iterators.
        typedef std::bidirectional_iterator_tag iterator_category;  //!< Iterator category.

        vec_iterator(pointer ptr = nullptr) : current{ptr} {};

        vec_iterator(const vec_iterator<T>& l) : current(l.current){};

        ~vec_iterator(){};

        //! it1 = it2;
        vec_iterator& operator=(const vec_iterator& rhs) {
                current = rhs.current;
                return *this;
        }

        //! ++it;
        vec_iterator operator++() {
                ++current;
                return *this;
        }

        //! it++;
        vec_iterator operator++(int) {
                ++current;
                return vec_iterator(current - 1);
        }

        //! --it;
        vec_iterator operator--() { return vec_iterator(--current); }

        //! it--;
        vec_iterator operator--(int) {
                current--;
                return vec_iterator(current + 1);
        }

        //! it = it1 - it2;
        difference_type operator-(const vec_iterator& rhs) { return current - rhs.current; }

        //! *it = x;
        reference operator*(void) { return *current; }

        //! x = *it;
        reference operator*(void)const { return *current; }

        //! it->
        pointer operator->(void)const {
                assert(current != nullptr);
                return current;
        }

        //! n + it
        friend vec_iterator operator+(difference_type n, vec_iterator it) {
                return vec_iterator(std::next(it, n));
        }

        //! it + n
        friend vec_iterator operator+(vec_iterator it, difference_type n) {
                return vec_iterator(std::next(it, n));
        }

        //! n - it
        friend vec_iterator operator-(difference_type n, vec_iterator it) {
                return vec_iterator(std::prev(it, n));
        }

        //! it - n
        friend vec_iterator operator-(vec_iterator it, difference_type n) {
                return vec_iterator(std::prev(it, n));
        }

        //! it1 == it2
        bool operator==(const vec_iterator& it) const { return (current == it.current); }

        //! it1 != it2
        bool operator!=(const vec_iterator& it) const { return (current != it.current); }

        //! << it
        friend std::ostream& operator<<(std::ostream& os, const vec_iterator<T>& v) { return os << *v; }

       private:
        T* current;
};