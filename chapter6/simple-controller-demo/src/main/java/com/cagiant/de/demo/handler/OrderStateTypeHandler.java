package com.cagiant.de.demo.handler;

import com.cagiant.de.demo.model.OrderState;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderStateTypeHandler extends BaseTypeHandler<OrderState> {
    @Override
    public OrderState getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return OrderState.value2OrderState(rs.getInt(columnName));
    }

    @Override
    public OrderState getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return OrderState.value2OrderState(rs.getInt(columnIndex));
    }

    @Override
    public OrderState getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return OrderState.value2OrderState(cs.getInt(columnIndex));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OrderState orderState, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, orderState.getValue());
    }
}
