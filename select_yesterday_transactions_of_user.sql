SELECT transactions.*
FROM transactions,
     accounts AS from_accounts,
     accounts AS to_accounts
WHERE transactions.from_account_id = from_accounts.id
  AND transactions.to_account_id = to_accounts.id
  AND (from_accounts.user_id = 1 OR to_accounts.user_id = 1)
  AND date
    > (now() - interval '1 day'):: date
  AND date
    < now():: date;
