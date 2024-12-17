alter table account add column active boolean null;
update account set active = true;
alter table account alter column active set not null;