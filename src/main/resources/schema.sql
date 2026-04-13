CREATE TABLE IF NOT EXISTS block (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    date DATE NOT NULL,
    hour INT NOT NULL,
    expected_goal VARCHAR(500),
    actual_result VARCHAR(1000),
    score INT,
    review VARCHAR(1000),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_block_user_date_hour UNIQUE (user_id, date, hour)
);

CREATE TABLE IF NOT EXISTS daily_summary (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    date DATE NOT NULL,
    ai_summary VARCHAR(2000),
    ai_problem VARCHAR(2000),
    ai_suggestion VARCHAR(2000),
    user_summary VARCHAR(2000),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_daily_summary_user_date UNIQUE (user_id, date)
);
