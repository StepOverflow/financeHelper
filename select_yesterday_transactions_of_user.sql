SELECT *
FROM transactions
WHERE from_account_id IN (
    SELECT id
    FROM accounts
    WHERE user_id = 1
)
   OR to_account_id IN (
    SELECT id
    FROM accounts
    WHERE user_id = 1
)
    AND date > (now() - interval '1 day')::date
  AND date < now()::date;
