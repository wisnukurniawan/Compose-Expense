```
Mandatory at least a user have 1 account before creating transaction
Account
id string -> remove this will remove transactions ref
updatedAt date -> updated every save
createdAt date -> not editable
type -> not editable
     OTHERS (CASH or INPUT)
     EWALLET
     BANK
name string -> not editable, unique
initialAmount long -> editable, accept minus
//#REF
currencyId -> not editable

Transaction
id string
updatedAt date -> updated every save
createdAt date -> not editable
transactionDate
amount long -> editable will affect amount, default 0, not accept minus
note string -> editable optional
recordType recordType
 INCOME
 EXPENSE
REF
accountId string
categoryId string
currencyId string

Currency
HARDCODED default IDR
id string
createdAt date
code string -> unique

Category
HARDCODED default others
id string
name string -> unique

```
