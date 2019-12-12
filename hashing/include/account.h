#ifndef ACCOUNT_H
#define ACCOUNT_H

#include <string>
#include <utility>

class Account {
       public:
        using AcctKey = std::tuple<std::string, int, int, int>;
        using data_type = int;

        std::string m_name;    //!< Client name.
        data_type bank_num;    //!< Bank number.
        data_type agency_num;  //!< Agency number.
        data_type acc_num;     //!< Account number.
        float m_balance;       //!< Money available.

        //! \brief Default constructor.
        Account(std::string name = "", data_type bank_n = 0, data_type agc_n = 0,
                data_type acc_n = 0, float value = 0)
            : m_name{name}, bank_num{bank_n}, agency_num{agc_n}, acc_num{acc_n}, m_balance{value} {}

        //! \brief Default desconstructor.
        ~Account() {}

        //! \brief Get hash made by client name, bank number, agency number and account number.
        //! \return Key associated to data.
        AcctKey getKey(void) { return std::make_tuple(m_name, bank_num, agency_num, acc_num); }

        //! \brief Tests equality.
        //! \return True if equal, false otherwise.
        bool operator==(const Account& rhs) const {
                if ((this->m_name != rhs.m_name) || (this->bank_num != rhs.bank_num) ||
                    (this->agency_num != rhs.agency_num) || (this->acc_num != rhs.acc_num)) {
                        return false;
                }

                return true;
        }

        //! \brief Extractor operator.
        friend std::ostream& operator<<(std::ostream& os, const Account& account) {
                os << "\n\tClient:\t\t" << account.m_name << std::endl;
                os << "\tBank:\t\t" << account.bank_num << std::endl;
                os << "\tAgency:\t\t" << account.agency_num << std::endl;
                os << "\tAccount:\t" << account.acc_num << std::endl;
                os << "\tBalance:\t" << account.m_balance << std::endl;

                return os;
        }
};

#endif