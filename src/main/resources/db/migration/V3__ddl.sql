alter table medicos add active boolean;
update medicos set active = true;
alter table medicos alter column active set not null;
