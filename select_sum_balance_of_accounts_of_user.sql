SELECT SUM(balance) AS total_balance
FROM accounts
         JOIN users ON user.id = accounts.user_id
WHERE user.id = 1;