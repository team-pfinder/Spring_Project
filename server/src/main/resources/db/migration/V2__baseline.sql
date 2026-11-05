create table accounts (
    id            bigint generated always as identity primary key,
    email         varchar(255) not null,
    password_hash varchar(255) not null,
    name          varchar(50)  not null,
    nickname      varchar(30)  not null,
    phone         varchar(20),
    status        varchar(20)  not null default 'ACTIVE',
    withdrawn_at  timestamptz,
    created_at    timestamptz  not null default now(),
    updated_at    timestamptz  not null default now()
);

create table account_roles (
    account_id bigint      not null references accounts (id),
    role       varchar(20) not null,
    granted_at timestamptz not null default now(),
    primary key (account_id, role)
);

create table refresh_tokens (
    id         bigint generated always as identity primary key,
    account_id bigint      not null references accounts (id) on delete cascade,
    token_hash varchar(64) not null unique,
    family_id  uuid        not null,
    expires_at timestamptz not null,
    revoked_at timestamptz,
    created_at timestamptz not null default now()
);

create table email_verifications (
    id         bigint generated always as identity primary key,
    email      varchar(255) not null,
    code_hash  varchar(64)  not null,
    purpose    varchar(20)  not null,
    attempts   int          not null default 0,
    expires_at timestamptz  not null,
    used_at    timestamptz,
    created_at timestamptz  not null default now()
);

create table host_profiles (
    account_id              bigint primary key references accounts (id),
    business_name           varchar(100) not null,
    business_number         varchar(20)  not null unique,
    representative_name     varchar(50)  not null,
    bank_name               varchar(50),
    bank_account_number_enc bytea,
    bank_account_holder_enc bytea,
    status                  varchar(30)  not null default 'PUBLISHED',
    created_at              timestamptz  not null default now(),
    updated_at              timestamptz  not null default now()
);

create table sanctions (
    id         bigint generated always as identity primary key,
    account_id bigint       not null references accounts (id),
    reason     varchar(300) not null,
    level      smallint     not null,
    starts_at  timestamptz  not null default now(),
    ends_at    timestamptz,
    revoked_at timestamptz,
    admin_id   bigint       references accounts (id),
    created_at timestamptz  not null default now()
);

create table spaces (
    id             bigint generated always as identity primary key,
    host_id        bigint       not null references accounts (id),
    name           varchar(100) not null,
    short_intro    varchar(300),
    description    text,
    address        varchar(200) not null,
    address_detail varchar(200),
    sido           varchar(20),
    sigungu        varchar(30),
    space_type     varchar(30)  not null,
    min_capacity   smallint     not null default 1,
    max_capacity   smallint     not null,
    use_hours      varchar(300),
    caution        varchar(450),
    contact_email  varchar(255),
    contact_phone  varchar(20),
    website_url    varchar(2000),
    approval_required boolean   not null default false,
    status         varchar(30)  not null default 'DRAFT',
    deleted_at     timestamptz,
    created_at     timestamptz  not null default now(),
    updated_at     timestamptz  not null default now()
);

create table space_facilities (
    space_id      bigint      not null references spaces (id) on delete cascade,
    facility_code varchar(30) not null,
    primary key (space_id, facility_code)
);

create table attachments (
    id            bigint generated always as identity primary key,
    owner_type    varchar(30)  not null,
    owner_id      bigint       not null,
    storage_key   varchar(500) not null,
    original_name varchar(255) not null,
    content_type  varchar(100) not null,
    size_bytes    bigint       not null,
    sort_order    smallint     not null default 0,
    created_at    timestamptz  not null default now()
);

create table price_rules (
    id          bigint generated always as identity primary key,
    space_id    bigint      not null references spaces (id),
    day_of_week smallint    not null,
    unit        varchar(10) not null,
    start_time  time,
    end_time   time,
    price      bigint      not null,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
);

create table reservations (
    id              bigint generated always as identity primary key,
    member_id       bigint       not null references accounts (id),
    host_id         bigint       not null references accounts (id),
    space_id        bigint       not null references spaces (id),
    start_at        timestamptz  not null,
    end_at          timestamptz  not null,
    guest_count     smallint     not null,
    price           bigint       not null,
    fee             bigint       not null default 0,
    status          varchar(30)  not null,
    request_message varchar(300),
    approved_at     timestamptz,
    payment_due_at  timestamptz,
    confirmed_at    timestamptz,
    completed_at    timestamptz,
    cancelled_at    timestamptz,
    created_at      timestamptz  not null default now(),
    updated_at      timestamptz  not null default now()
);

create table reservation_events (
    id             bigint generated always as identity primary key,
    reservation_id bigint      not null references reservations (id),
    from_status    varchar(30),
    to_status      varchar(30) not null,
    actor_type     varchar(20) not null,
    actor_id       bigint,
    reason         varchar(500),
    created_at     timestamptz not null default now()
);

create table wallets (
    account_id bigint      primary key references accounts (id),
    balance    bigint      not null default 0,
    updated_at timestamptz not null default now()
);

create table wallet_entries (
    id         bigint generated always as identity primary key,
    account_id bigint      not null references accounts (id),
    entry_type varchar(20) not null,
    amount     bigint      not null,
    ref_type   varchar(30),
    ref_id     bigint,
    created_at timestamptz not null default now()
);

create table charges (
    id             bigint generated always as identity primary key,
    account_id     bigint      not null references accounts (id),
    amount         bigint      not null,
    depositor_name varchar(50) not null,
    status         varchar(20) not null default 'REQUESTED',
    admin_id       bigint      references accounts (id),
    processed_at   timestamptz,
    created_at     timestamptz not null default now()
);

create table settlements (
    id             bigint generated always as identity primary key,
    reservation_id bigint      not null unique references reservations (id),
    host_id        bigint      not null references accounts (id),
    gross_amount   bigint      not null,
    fee_amount     bigint      not null,
    net_amount     bigint      not null,
    settled_at     timestamptz not null default now()
);

create table withdrawals (
    id             bigint generated always as identity primary key,
    account_id     bigint      not null references accounts (id),
    amount         bigint      not null,
    bank_name           varchar(50) not null,
    account_number_enc  bytea       not null,
    account_holder_enc  bytea       not null,
    status         varchar(20) not null default 'REQUESTED',
    admin_id       bigint      references accounts (id),
    processed_at   timestamptz,
    created_at     timestamptz not null default now()
);

create table inspections (
    id           bigint generated always as identity primary key,
    space_id     bigint      not null references spaces (id),
    status       varchar(30) not null default 'PENDING_INSPECTION',
    reason       varchar(500),
    admin_id     bigint      references accounts (id),
    requested_at timestamptz not null default now(),
    processed_at timestamptz
);

create table job_runs (
    id              bigint generated always as identity primary key,
    job_name        varchar(50) not null,
    status          varchar(20) not null default 'RUNNING',
    processed_count int         not null default 0,
    message         varchar(500),
    started_at      timestamptz not null default now(),
    finished_at     timestamptz
);

create table messages (
    id             bigint generated always as identity primary key,
    reservation_id bigint        not null references reservations (id),
    sender_id      bigint        not null references accounts (id),
    content        varchar(2000) not null,
    created_at     timestamptz   not null default now()
);

create table reports (
    id           bigint generated always as identity primary key,
    reporter_id  bigint       not null references accounts (id),
    target_type  varchar(20)  not null,
    target_id    bigint       not null,
    reason       varchar(500) not null,
    status       varchar(20)  not null default 'RECEIVED',
    admin_id     bigint       references accounts (id),
    processed_at timestamptz,
    created_at   timestamptz  not null default now()
);

create table reviews (
    id             bigint generated always as identity primary key,
    reservation_id bigint        not null unique references reservations (id),
    space_id       bigint        not null references spaces (id),
    member_id      bigint        not null references accounts (id),
    rating         smallint      not null,
    content        varchar(2000) not null,
    blinded_at     timestamptz,
    created_at     timestamptz   not null default now()
);

create table review_replies (
    id         bigint generated always as identity primary key,
    review_id  bigint        not null unique references reviews (id),
    host_id    bigint        not null references accounts (id),
    content    varchar(2000) not null,
    created_at timestamptz   not null default now()
);

create table qnas (
    id         bigint generated always as identity primary key,
    space_id   bigint        not null references spaces (id),
    member_id  bigint        not null references accounts (id),
    title      varchar(200)  not null,
    content    varchar(2000) not null,
    status     varchar(20)   not null default 'OPEN',
    created_at timestamptz   not null default now()
);

create table qna_replies (
    id         bigint generated always as identity primary key,
    qna_id     bigint        not null unique references qnas (id),
    host_id    bigint        not null references accounts (id),
    content    varchar(2000) not null,
    created_at timestamptz   not null default now()
);

create table notices (
    id         bigint generated always as identity primary key,
    title      varchar(200) not null,
    content    text         not null,
    important  boolean      not null default false,
    created_at timestamptz  not null default now(),
    updated_at timestamptz  not null default now()
);

create table helps (
    id         bigint generated always as identity primary key,
    category   varchar(50)  not null,
    title      varchar(200) not null,
    content    text         not null,
    created_at timestamptz  not null default now(),
    updated_at timestamptz  not null default now()
);
