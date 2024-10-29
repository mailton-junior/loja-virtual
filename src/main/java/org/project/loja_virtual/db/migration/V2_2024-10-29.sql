CREATE OR REPLACE FUNCTION ValidatePersonKey() RETURNS trigger
    LANGUAGE plpgsql
    AS $$

  declare existe integer;

  begin
    existe = (select count(1) from individual_person where id = NEW.person_id);
    if(existe <=0 ) then
     existe = (select count(1) from entity_person where id = NEW.person_id);
    if (existe <= 0) then
      raise exception 'Não foi encontrado o ID ou PK da pessoa para realizar a associação';
     end if;
    end if;
    RETURN NEW;
  end;
  $$;

create trigger ValidatePersonKeyProductReview
before update
on product_review
for each row
execute procedure ValidatePersonKey();

create trigger ValidatePersonKeyProductReview2
before insert
on product_review
for each row
execute procedure ValidatePersonKey();

--
create trigger ValidatePersonKeyaccount_payable
before update
on account_payable
for each row
execute procedure ValidatePersonKey();

create trigger ValidatePersonKeyaccount_payable2
before insert
on account_payable
for each row
execute procedure ValidatePersonKey();


CREATE or replace FUNCTION ValidatePersonKey2() RETURNS trigger
    LANGUAGE plpgsql
    AS $$

  declare existe integer;

  begin
    existe = (select count(1) from individual_person where id = NEW.supplier_person_id);
    if(existe <=0 ) then
     existe = (select count(1) from entity_person where id = NEW.supplier_person_id);
    if (existe <= 0) then
      raise exception 'Não foi encontrado o ID ou PK da pessoa para realizar a associação';
     end if;
    end if;
    RETURN NEW;
  end;
  $$;

  create trigger ValidatePersonKeyaccount_payablsupplier_person_id
  before update
  on account_payable
  for each row
  execute procedure ValidatePersonKey2();

  create trigger ValidatePersonKeyaccount_payablesupplier_person_id
  before insert
  on account_payable
  for each row
  execute procedure ValidatePersonKey2();

  --

  create trigger ValidatePersonKeyaccount_payable
  before update
  on account_receivable
  for each row
  execute procedure ValidatePersonKey();

  create trigger ValidatePersonKeyaccount_payable2
  before insert
  on account_receivable
  for each row
  execute procedure ValidatePersonKey();

--

create trigger ValidatePersonKeyaccount_payable
before update
on address
for each row
execute procedure ValidatePersonKey();

create trigger ValidatePersonKeyaccount_payable2
before insert
on address
for each row
execute procedure ValidatePersonKey();

--

create trigger ValidatePersonKeyaccount_payable
before update
on purchase_invoice
for each row
execute procedure ValidatePersonKey();

create trigger ValidatePersonKeyaccount_payable2
before insert
on purchase_invoice
for each row
execute procedure ValidatePersonKey();

--

create trigger ValidatePersonKeyaccount_payable
before update
on users
for each row
execute procedure ValidatePersonKey();

create trigger ValidatePersonKeyaccount_payable2
before insert
on users
for each row
execute procedure ValidatePersonKey();

--


create trigger ValidatePersonKeyaccount_payable
before update
on sales_and_buy_online_store
for each row
execute procedure ValidatePersonKey();

create trigger ValidatePersonKeyaccount_payable2
before insert
on sales_and_buy_online_store
for each row
execute procedure ValidatePersonKey();