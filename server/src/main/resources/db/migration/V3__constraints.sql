create unique index accounts_email_key on accounts (lower(email));

alter table accounts add constraint accounts_status_check
    check (status in ('ACTIVE', 'WITHDRAWN'));

alter table account_roles add constraint account_roles_role_check
    check (role in ('MEMBER', 'HOST', 'ADMIN'));

alter table email_verifications add constraint email_verifications_attempts_check
    check (attempts >= 0);

alter table email_verifications add constraint email_verifications_purpose_check
    check (purpose in ('SIGNUP', 'PASSWORD_RESET'));

alter table host_profiles add constraint host_profiles_status_check
    check (status in ('PENDING_INSPECTION', 'INSPECTION_REJECTED', 'PUBLISHED', 'SUSPENDED'));

alter table sanctions add constraint sanctions_level_check
    check (level >= 1 and level <= 3);

alter table spaces add constraint spaces_status_check
    check (status in ('DRAFT', 'PENDING_INSPECTION', 'INSPECTION_REJECTED', 'PUBLISHED', 'SUSPENDED', 'CLOSED'));

alter table spaces add constraint spaces_capacity_check
    check (min_capacity > 0 and max_capacity >= min_capacity);

alter table space_facilities add constraint space_facilities_code_check
    check (facility_code in ('PARKING', 'WIFI', 'PROJECTOR', 'SOUND', 'KITCHEN', 'REFRIGERATOR',
                             'MICROWAVE', 'AIR_CONDITIONER', 'TABLE', 'CHAIR', 'BATHROOM', 'ELEVATOR'));

alter table attachments add constraint attachments_size_check
    check (size_bytes >= 0);

alter table price_rules add constraint price_rules_day_check
    check (day_of_week between 1 and 7);

alter table price_rules add constraint price_rules_unique_slot
    unique (space_id, day_of_week, start_time, end_time);

create unique index price_rules_day_unique
    on price_rules (space_id, day_of_week) where unit = 'DAY';

alter table price_rules add constraint price_rules_no_overlap
    exclude using gist (space_id with =, day_of_week with =,
        tsrange(timestamp '2000-01-01' + start_time, timestamp '2000-01-01' + end_time) with &&)
    where (unit = 'HOUR');

alter table price_rules add constraint price_rules_unit_check
    check (unit in ('HOUR', 'DAY'));

alter table price_rules add constraint price_rules_price_check
    check (price >= 0);

alter table price_rules add constraint price_rules_time_check
    check (
        (unit = 'DAY' and start_time is null and end_time is null)
        or (unit = 'HOUR' and start_time is not null and end_time is not null and end_time > start_time)
    );

alter table reservations add constraint reservations_time_check
    check (end_at > start_at);

alter table reservations add constraint reservations_counts_check
    check (guest_count > 0 and price >= 0 and fee >= 0);

alter table reservations add constraint reservations_status_check
    check (
        status in ('PENDING_APPROVAL', 'PENDING_PAYMENT', 'CONFIRMED', 'COMPLETED',
                   'CANCELLED', 'REFUNDED', 'REJECTED', 'EXPIRED')
    );

alter table reservations add constraint reservations_no_overlap
    exclude using gist (space_id with =, tstzrange(start_at, end_at, '[)') with &&)
    where (status in ('PENDING_APPROVAL', 'PENDING_PAYMENT', 'CONFIRMED', 'COMPLETED'));

alter table reservation_events add constraint reservation_events_actor_check
    check (actor_type in ('MEMBER', 'HOST', 'ADMIN', 'SYSTEM'));

alter table wallets add constraint wallets_balance_check
    check (balance >= 0);

alter table wallet_entries add constraint wallet_entries_type_check
    check (entry_type in ('CHARGE', 'HOLD', 'RELEASE', 'REFUND', 'SETTLE', 'FEE', 'WITHDRAWAL'));

alter table charges add constraint charges_amount_check check (amount > 0);

alter table charges add constraint charges_status_check
    check (status in ('REQUESTED', 'APPROVED', 'REJECTED'));

alter table settlements add constraint settlements_amount_check
    check (gross_amount >= 0 and fee_amount >= 0 and net_amount >= 0);

alter table withdrawals add constraint withdrawals_amount_check check (amount > 0);

alter table withdrawals add constraint withdrawals_status_check
    check (status in ('REQUESTED', 'APPROVED', 'PAID', 'REJECTED'));

alter table job_runs add constraint job_runs_status_check
    check (status in ('RUNNING', 'SUCCESS', 'FAILED'));

alter table inspections add constraint inspections_status_check
    check (status in ('PENDING_INSPECTION', 'INSPECTION_REJECTED', 'PUBLISHED', 'SUSPENDED'));

create index refresh_tokens_account_idx on refresh_tokens (account_id);
create index email_verifications_email_idx on email_verifications (email, purpose);
create index sanctions_account_idx on sanctions (account_id, starts_at desc);
create index spaces_host_idx on spaces (host_id);
create index attachments_owner_idx on attachments (owner_type, owner_id);
create index price_rules_space_idx on price_rules (space_id);
create index reservations_member_idx on reservations (member_id, start_at desc);
create index reservations_host_idx on reservations (host_id, start_at desc);
create index reservation_events_reservation_idx on reservation_events (reservation_id, created_at);
create index wallet_entries_account_idx on wallet_entries (account_id, created_at desc);
create index charges_account_idx on charges (account_id, created_at desc);
create index withdrawals_account_idx on withdrawals (account_id, created_at desc);
create index inspections_space_idx on inspections (space_id, requested_at desc);

alter table spaces add constraint spaces_space_type_check
    check (space_type in ('PARTY_ROOM', 'EMPTY_SPACE', 'ROOFTOP', 'BRIDAL_SHOWER', 'CLUB', 'WORKSHOP'));

alter table messages add constraint messages_content_check
    check (length(content) > 0);

alter table reports add constraint reports_target_type_check
    check (target_type in ('SPACE', 'RESERVATION', 'REVIEW', 'QNA', 'ACCOUNT'));

alter table reports add constraint reports_status_check
    check (status in ('RECEIVED', 'ACCEPTED', 'REJECTED'));

create index messages_reservation_idx on messages (reservation_id, created_at);
create index reports_target_idx on reports (target_type, target_id);
