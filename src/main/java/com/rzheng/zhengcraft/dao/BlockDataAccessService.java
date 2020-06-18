/*
 * Copyright 2020-2020 Richard R. Zheng (https://github.com/rzheng95)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to
 * do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 *
 */

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
        String sql = "INSERT INTO block (blocks_placed, blocks_destroyed, id, material) VALUES (?, ?, ?, ?)";

        Optional<Block> isBlock = this.getBlockByIdAndMaterial.apply(block.getId(), block.getMaterial());
        if (!isBlock.isEmpty()) { // update
            sql = "UPDATE block SET blocks_placed = ?, blocks_destroyed = ? WHERE id = ? AND material = ?";
        }

        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            if (!isBlock.isEmpty()) { // already exists
                statement.setLong(1, isBlock.get().getBlocksPlaced() + block.getBlocksPlaced());
                statement.setLong(2, isBlock.get().getBlockDestroyed() + block.getBlockDestroyed());
            } else {
                statement.setLong(1, block.getBlocksPlaced());
                statement.setLong(2, block.getBlockDestroyed());
            }
            statement.setObject(3, block.getId());
            statement.setString(4, block.getMaterial());
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
                long blocks_destroyed = resultSet.getLong("blocks_destroyed");
                blocks.add(new Block(blockId, material, blocks_placed, blocks_destroyed));
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
                long blocks_destroyed = resultSet.getLong("blocks_destroyed");
                block = new Block(blockId, blockMaterial, blocks_placed, blocks_destroyed);
            }
            resultSet.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return Optional.ofNullable(block);
    };


}
