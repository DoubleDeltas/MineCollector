package com.doubledeltas.minecollector.item.itemCode;

import com.doubledeltas.minecollector.MineCollector;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.io.*;

public interface ItemCode extends Serializable {
    String getPathName();

    NamespacedKey PERSISTENT_DATA_KEY = NamespacedKey.fromString("item_code", MineCollector.getInstance());

    PersistentDataType<byte[], ItemCode> PERSISTENT_DATA_TYPE = new DataType();

    class DataType implements PersistentDataType<byte[], ItemCode> {
        @Override
        @Nonnull
        public Class<byte[]> getPrimitiveType() {
            return byte[].class;
        }

        @Override
        @Nonnull
        public Class<ItemCode> getComplexType() {
            return ItemCode.class;
        }

        @Override
        @Nonnull
        public byte[] toPrimitive(@Nonnull ItemCode complex, @Nonnull PersistentDataAdapterContext context) {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(complex);
                return bos.toByteArray();
            } catch (IOException e) {
                throw new IllegalStateException("Failed to serialize object", e);
            }
        }

        @Override
        @Nonnull
        public ItemCode fromPrimitive(@Nonnull byte[] primitive, @Nonnull PersistentDataAdapterContext context) {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(primitive);
                 ObjectInputStream ois = new ObjectInputStream(bis)) {
                Object obj = ois.readObject();
                return (ItemCode) obj;
            } catch (IOException | ClassNotFoundException e) {
                throw new IllegalStateException("Failed to deserialize object", e);
            }
        }
    };
}
