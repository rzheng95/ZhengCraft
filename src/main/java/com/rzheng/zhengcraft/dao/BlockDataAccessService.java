package com.rzheng.zhengcraft.dao;

import com.rzheng.zhengcraft.datasource.PostgresDatasource;
import com.rzheng.zhengcraft.entities.Block;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class BlockDataAccessService {
    private Connection connection;

    public BlockDataAccessService() {
        try {
            this.connection = PostgresDatasource.hikariDataSource().getConnection();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public Predicate<Block> addBlock = block -> {
        String sql = "INSERT INTO block (blocks_placed, id, material) VALUES (?, ?, ?)";

        Optional<Block> isBlock = this.getBlockByIdAndMaterial.apply(block.getId(), block.getMaterial());
        if (!isBlock.isEmpty()) { // update
            sql = "UPDATE block SET blocks_placed = ? WHERE id = ? AND material = ?";
        }

        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            if (!isBlock.isEmpty()) {
                statement.setLong(1, isBlock.get().getBlocksPlaced() + block.getBlocksPlaced());
            } else {
                statement.setLong(1, block.getBlocksPlaced());
            }
            statement.setObject(2, block.getId());
            statement.setString(3, block.getMaterial());
            statement.executeUpdate();
            return true;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return false;
    };

    public Function<UUID, Optional<List<Block>>> getBlocksById = id -> {

        List<Block> blocks = new ArrayList<>();
        final String sql = "SELECT * FROM block WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID blockId = UUID.fromString(resultSet.getString("id"));
                String material = resultSet.getString("material");
                long blocks_placed = resultSet.getLong("blocks_placed");
                blocks.add(new Block(blockId, material, blocks_placed));
            }

            resultSet.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return Optional.ofNullable(blocks);
    };

    public BiFunction<UUID, String, Optional<Block>> getBlockByIdAndMaterial = (id, material) -> {
        Block block = null;

        final String sql = "SELECT * FROM block WHERE id = ? AND material = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setObject(1, id);
            statement.setString(2, material);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                UUID blockId = UUID.fromString(resultSet.getString("id"));
                String blockMaterial = resultSet.getString("material");
                long blocks_placed = resultSet.getLong("blocks_placed");
                block = new Block(blockId, blockMaterial, blocks_placed);
            }
            resultSet.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return Optional.ofNullable(block);
    };


}
