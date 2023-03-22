SELECT users.email, SUM(balance) AS total_balance
FROM accounts
         JOIN users ON users.id = accounts.user_id
group by users.email;
