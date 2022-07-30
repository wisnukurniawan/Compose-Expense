# How to calculate edited transaction if balance amount has edited?

## Background
An Expense App have Account (cash/bank/ewallet/stock/etc), each Account have balance and record transactions (income, expense)

Use case:
- Initial balance 0
- Create income X 10.000 -> balance become 10.000
- Edit income X to 20.000 -> balance become 20.000
- Edit balance to 0 -> balance become 0
- Edit income X to 30.000 -> balance become 10.000 

PS: why it become 10.000 not 30.000?, because the income is already accumulated in the account balance, so that any increased/reduced transaction amount only calculate the diff not whole amount

```
balance edited time before
transaction_amount record

prev_transaction_amount = get_transaction_amount_before_balance_edited
current_balance = current_balance + (new_transaction_amount - prev_transaction_amount)
```

### Approaches
The following sections would evaluate possible approaches:

#### Approach #1
Record every edited balance and transaction amount

##### Cons:
Our storage will be huge if user do edited data often

#### Approach #2
Create base amount in transaction, whenever balance edited, base amount also updated. Base amount will be used as `prev_transaction_amount`

##### Cons:
Updating base amount will takes time if user have many transaction

### Decision Log 
#### Proceed with Approach 1
Don't select approach 2 because it require more write operation than approach 1
