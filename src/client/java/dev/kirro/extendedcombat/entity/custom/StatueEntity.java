package dev.kirro.extendedcombat.entity.custom;

import dev.kirro.extendedcombat.item.ModItems;
//import dev.kirro.extendedcombat.screen.StatueScreen;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.*;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class StatueEntity extends LivingEntity {
    private static final EulerAngle DEFAULT_HEAD_ROTATION = new EulerAngle(0.0F, 0.0F, 0.0F);
    private static final EulerAngle DEFAULT_BODY_ROTATION = new EulerAngle(0.0F, 0.0F, 0.0F);
    private static final EulerAngle DEFAULT_LEFT_ARM_ROTATION = new EulerAngle(-10.0F, 0.0F, -10.0F);
    private static final EulerAngle DEFAULT_RIGHT_ARM_ROTATION = new EulerAngle(-10.0F, 0.0F, 10.0F);
    private static final EulerAngle DEFAULT_LEFT_LEG_ROTATION = new EulerAngle(-1.0F, 0.0F, -1.0F);
    private static final EulerAngle DEFAULT_RIGHT_LEG_ROTATION = new EulerAngle(1.0F, 0.0F, 1.0F);
    private static final EntityDimensions MARKER_DIMENSIONS = EntityDimensions.fixed(0.0F, 0.0F);
    private static final EntityDimensions SMALL_DIMENSIONS = EntityType.ARMOR_STAND.getDimensions().scaled(0.5F).withEyeHeight(0.9875F);

    public static final int SMALL_FLAG = 1;
    public static final int SHOW_ARMS_FLAG = 4;
    public static final int HIDE_BASE_PLATE_FLAG = 8;
    public static final int MARKER_FLAG = 16;
    public static final TrackedData<Byte> ARMOR_STAND_FLAGS = DataTracker.registerData(StatueEntity.class, TrackedDataHandlerRegistry.BYTE);
    public static final TrackedData<EulerAngle> TRACKER_HEAD_ROTATION = DataTracker.registerData(StatueEntity.class, TrackedDataHandlerRegistry.ROTATION);
    public static final TrackedData<EulerAngle> TRACKER_BODY_ROTATION = DataTracker.registerData(StatueEntity.class, TrackedDataHandlerRegistry.ROTATION);
    public static final TrackedData<EulerAngle> TRACKER_LEFT_ARM_ROTATION = DataTracker.registerData(StatueEntity.class, TrackedDataHandlerRegistry.ROTATION);
    public static final TrackedData<EulerAngle> TRACKER_RIGHT_ARM_ROTATION = DataTracker.registerData(StatueEntity.class, TrackedDataHandlerRegistry.ROTATION);
    public static final TrackedData<EulerAngle> TRACKER_LEFT_LEG_ROTATION = DataTracker.registerData(StatueEntity.class, TrackedDataHandlerRegistry.ROTATION);
    public static final TrackedData<EulerAngle> TRACKER_RIGHT_LEG_ROTATION = DataTracker.registerData(StatueEntity.class, TrackedDataHandlerRegistry.ROTATION);
    private static final Predicate<Entity> RIDEABLE_MINECART_PREDICATE = entity -> entity instanceof AbstractMinecartEntity
            && ((AbstractMinecartEntity)entity).getMinecartType() == AbstractMinecartEntity.Type.RIDEABLE;
    public static float prevYaw;
    private final DefaultedList<ItemStack> heldItems = DefaultedList.ofSize(2, ItemStack.EMPTY);
    private final DefaultedList<ItemStack> armorItems = DefaultedList.ofSize(4, ItemStack.EMPTY);
    private boolean invisible;
    public long lastHitTime;
    private int disabledSlots;
    private EulerAngle headRotation = DEFAULT_HEAD_ROTATION;
    private EulerAngle bodyRotation = DEFAULT_BODY_ROTATION;
    private EulerAngle leftArmRotation = DEFAULT_LEFT_ARM_ROTATION;
    private EulerAngle rightArmRotation = DEFAULT_RIGHT_ARM_ROTATION;
    private EulerAngle leftLegRotation = DEFAULT_LEFT_LEG_ROTATION;
    private EulerAngle rightLegRotation = DEFAULT_RIGHT_LEG_ROTATION;

    public StatueEntity(EntityType<? extends StatueEntity> type, World world) {
        super(type, world);
    }

    private Direction facingDirection;

    public void setFacingDirection(Direction direction) {
        this.facingDirection = direction;
    }

    public Direction getFacingDirection() {
        return this.facingDirection != null ? this.facingDirection : Direction.EAST;
    }

    /*@Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player.isSneaking() && hand == Hand.MAIN_HAND) {
            if (!player.getWorld().isClient) {
                Screen currentScreen = MinecraftClient.getInstance().currentScreen;
                //MinecraftClient.getInstance().setScreen(
                //        new StatueScreen(Text.empty(), currentScreen)
                //);
            }
            return ActionResult.SUCCESS;
        }
        return super.interact(player, hand);
    }*/

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1)
                .add(EntityAttributes.GENERIC_STEP_HEIGHT, 0.0);
    }

    @Override
    public void calculateDimensions() {
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        super.calculateDimensions();
        this.setPosition(d, e, f);
    }

    private boolean canClip() {
        return !this.isMarker() && !this.hasNoGravity();
    }

    @Override
    public boolean canMoveVoluntarily() {
        return super.canMoveVoluntarily() && this.canClip();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ARMOR_STAND_FLAGS, (byte)0);
        builder.add(TRACKER_HEAD_ROTATION, DEFAULT_HEAD_ROTATION);
        builder.add(TRACKER_BODY_ROTATION, DEFAULT_BODY_ROTATION);
        builder.add(TRACKER_LEFT_ARM_ROTATION, DEFAULT_LEFT_ARM_ROTATION);
        builder.add(TRACKER_RIGHT_ARM_ROTATION, DEFAULT_RIGHT_ARM_ROTATION);
        builder.add(TRACKER_LEFT_LEG_ROTATION, DEFAULT_LEFT_LEG_ROTATION);
        builder.add(TRACKER_RIGHT_LEG_ROTATION, DEFAULT_RIGHT_LEG_ROTATION);
    }

    @Override
    public Iterable<ItemStack> getHandItems() {
        return this.heldItems;
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return this.armorItems;
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return switch (slot.getType()) {
            case HAND -> this.heldItems.get(slot.getEntitySlotId());
            case HUMANOID_ARMOR -> this.armorItems.get(slot.getEntitySlotId());
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public boolean canUseSlot(EquipmentSlot slot) {
        return slot != EquipmentSlot.BODY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        this.processEquippedStack(stack);
        switch (slot.getType()) {
            case HAND:
                this.onEquipStack(slot, this.heldItems.set(slot.getEntitySlotId(), stack), stack);
                break;
            case HUMANOID_ARMOR:
                this.onEquipStack(slot, this.armorItems.set(slot.getEntitySlotId(), stack), stack);
        }
    }

    @Override
    public boolean canEquip(ItemStack stack) {
        EquipmentSlot equipmentSlot = this.getPreferredEquipmentSlot(stack);
        return this.getEquippedStack(equipmentSlot).isEmpty() && !this.isSlotDisabled(equipmentSlot);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        NbtList nbtList = new NbtList();

        for (ItemStack itemStack : this.armorItems) {
            nbtList.add(itemStack.encodeAllowEmpty(this.getRegistryManager()));
        }

        nbt.put("ArmorItems", nbtList);
        NbtList nbtList2 = new NbtList();

        for (ItemStack itemStack2 : this.heldItems) {
            nbtList2.add(itemStack2.encodeAllowEmpty(this.getRegistryManager()));
        }

        nbt.put("HandItems", nbtList2);
        nbt.putBoolean("Invisible", this.isInvisible());
        nbt.putBoolean("Small", this.isSmall());
        nbt.putBoolean("ShowArms", this.shouldShowArms());
        nbt.putInt("DisabledSlots", this.disabledSlots);
        nbt.putBoolean("NoBasePlate", this.shouldHideBasePlate());
        if (this.isMarker()) {
            nbt.putBoolean("Marker", this.isMarker());
        }

        nbt.put("Pose", this.poseToNbt());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("ArmorItems", NbtElement.LIST_TYPE)) {
            NbtList nbtList = nbt.getList("ArmorItems", NbtElement.COMPOUND_TYPE);

            for (int i = 0; i < this.armorItems.size(); i++) {
                NbtCompound nbtCompound = nbtList.getCompound(i);
                this.armorItems.set(i, ItemStack.fromNbtOrEmpty(this.getRegistryManager(), nbtCompound));
            }
        }

        if (nbt.contains("HandItems", NbtElement.LIST_TYPE)) {
            NbtList nbtList = nbt.getList("HandItems", NbtElement.COMPOUND_TYPE);

            for (int i = 0; i < this.heldItems.size(); i++) {
                NbtCompound nbtCompound = nbtList.getCompound(i);
                this.heldItems.set(i, ItemStack.fromNbtOrEmpty(this.getRegistryManager(), nbtCompound));
            }
        }

        this.setInvisible(nbt.getBoolean("Invisible"));
        this.setSmall(nbt.getBoolean("Small"));
        this.setShowArms(nbt.getBoolean("ShowArms"));
        this.disabledSlots = nbt.getInt("DisabledSlots");
        this.setHideBasePlate(nbt.getBoolean("NoBasePlate"));
        this.setMarker(nbt.getBoolean("Marker"));
        this.noClip = !this.canClip();
        NbtCompound nbtCompound2 = nbt.getCompound("Pose");
        this.readPoseNbt(nbtCompound2);
    }

    private void readPoseNbt(NbtCompound nbt) {
        NbtList nbtList = nbt.getList("Head", NbtElement.FLOAT_TYPE);
        this.setHeadRotation(nbtList.isEmpty() ? DEFAULT_HEAD_ROTATION : new EulerAngle(nbtList));
        NbtList nbtList2 = nbt.getList("Body", NbtElement.FLOAT_TYPE);
        this.setBodyRotation(nbtList2.isEmpty() ? DEFAULT_BODY_ROTATION : new EulerAngle(nbtList2));
        NbtList nbtList3 = nbt.getList("LeftArm", NbtElement.FLOAT_TYPE);
        this.setLeftArmRotation(nbtList3.isEmpty() ? DEFAULT_LEFT_ARM_ROTATION : new EulerAngle(nbtList3));
        NbtList nbtList4 = nbt.getList("RightArm", NbtElement.FLOAT_TYPE);
        this.setRightArmRotation(nbtList4.isEmpty() ? DEFAULT_RIGHT_ARM_ROTATION : new EulerAngle(nbtList4));
    }

    private NbtCompound poseToNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        if (!DEFAULT_HEAD_ROTATION.equals(this.headRotation)) {
            nbtCompound.put("Head", this.headRotation.toNbt());
        }

        if (!DEFAULT_BODY_ROTATION.equals(this.bodyRotation)) {
            nbtCompound.put("Body", this.bodyRotation.toNbt());
        }

        if (!DEFAULT_LEFT_ARM_ROTATION.equals(this.leftArmRotation)) {
            nbtCompound.put("LeftArm", this.leftArmRotation.toNbt());
        }

        if (!DEFAULT_RIGHT_ARM_ROTATION.equals(this.rightArmRotation)) {
            nbtCompound.put("RightArm", this.rightArmRotation.toNbt());
        }

        return nbtCompound;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void pushAway(Entity entity) {
    }

    @Override
    protected void tickCramming() {
        for (Entity entity : this.getWorld().getOtherEntities(this, this.getBoundingBox(), RIDEABLE_MINECART_PREDICATE)) {
            if (this.squaredDistanceTo(entity) <= 0.2) {
                entity.pushAwayFrom(this);
            }
        }
    }

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (this.isMarker() || itemStack.isOf(Items.NAME_TAG)) {
            return ActionResult.PASS;
        } else if (player.isSpectator()) {
            return ActionResult.SUCCESS;
        } else if (player.getWorld().isClient) {
            return ActionResult.CONSUME;
        } else {
            EquipmentSlot equipmentSlot = this.getPreferredEquipmentSlot(itemStack);
            if (itemStack.isEmpty()) {
                EquipmentSlot equipmentSlot2 = this.getSlotFromPosition(hitPos);
                EquipmentSlot equipmentSlot3 = this.isSlotDisabled(equipmentSlot2) ? equipmentSlot : equipmentSlot2;
                if (this.hasStackEquipped(equipmentSlot3) && this.equip(player, equipmentSlot3, itemStack, hand)) {
                    return ActionResult.SUCCESS;
                }
            } else {
                if (this.isSlotDisabled(equipmentSlot)) {
                    return ActionResult.FAIL;
                }

                if (equipmentSlot.getType() == EquipmentSlot.Type.HAND && !this.shouldShowArms()) {
                    return ActionResult.FAIL;
                }

                if (this.equip(player, equipmentSlot, itemStack, hand)) {
                    return ActionResult.SUCCESS;
                }
            }

            return ActionResult.PASS;
        }
    }

    private EquipmentSlot getSlotFromPosition(Vec3d hitPos) {
        EquipmentSlot equipmentSlot = EquipmentSlot.MAINHAND;
        boolean bl = this.isSmall();
        double d = hitPos.y / (this.getScale() * this.getScaleFactor());
        if (d >= 0.1 && d < 0.1 + (bl ? 0.8 : 0.45) && this.hasStackEquipped(EquipmentSlot.FEET)) {
            equipmentSlot = EquipmentSlot.FEET;
        } else if (d >= 0.9 + (bl ? 0.3 : 0.0) && d < 0.9 + (bl ? 1.0 : 0.7) && this.hasStackEquipped(EquipmentSlot.CHEST)) {
            equipmentSlot = EquipmentSlot.CHEST;
        } else if (d >= 0.4 && d < 0.4 + (bl ? 1.0 : 0.8) && this.hasStackEquipped(EquipmentSlot.LEGS)) {
            equipmentSlot = EquipmentSlot.LEGS;
        } else if (d >= 1.6 && this.hasStackEquipped(EquipmentSlot.HEAD)) {
            equipmentSlot = EquipmentSlot.HEAD;
        } else if (!this.hasStackEquipped(EquipmentSlot.MAINHAND) && this.hasStackEquipped(EquipmentSlot.OFFHAND)) {
            equipmentSlot = EquipmentSlot.OFFHAND;
        }

        return equipmentSlot;
    }

    private boolean isSlotDisabled(EquipmentSlot slot) {
        return (this.disabledSlots & 1 << slot.getArmorStandSlotId()) != 0 || slot.getType() == EquipmentSlot.Type.HAND && !this.shouldShowArms();
    }

    private boolean equip(PlayerEntity player, EquipmentSlot slot, ItemStack stack, Hand hand) {
        ItemStack itemStack = this.getEquippedStack(slot);
        if (!itemStack.isEmpty() && (this.disabledSlots & 1 << slot.getArmorStandSlotId() + 8) != 0) {
            return false;
        } else if (itemStack.isEmpty() && (this.disabledSlots & 1 << slot.getArmorStandSlotId() + 16) != 0) {
            return false;
        } else if (player.isInCreativeMode() && itemStack.isEmpty() && !stack.isEmpty()) {
            this.equipStack(slot, stack.copyWithCount(1));
            return true;
        } else if (stack.isEmpty() || stack.getCount() <= 1) {
            this.equipStack(slot, stack);
            player.setStackInHand(hand, itemStack);
            return true;
        } else if (!itemStack.isEmpty()) {
            return false;
        } else {
            this.equipStack(slot, stack.split(1));
            return true;
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isRemoved()) {
            return false;
        } else if (this.getWorld() instanceof ServerWorld serverWorld) {
            if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                this.kill();
                return false;
            } else if (this.isInvulnerableTo(source) || this.invisible || this.isMarker()) {
                return false;
            } else if (source.isIn(DamageTypeTags.IS_EXPLOSION)) {
                this.onBreak(serverWorld, source);
                this.kill();
                return false;
            } else if (source.isIn(DamageTypeTags.IGNITES_ARMOR_STANDS)) {
                if (this.isOnFire()) {
                    this.updateHealth(serverWorld, source, 0.15F);
                } else {
                    this.setOnFireFor(5.0F);
                }

                return false;
            } else if (source.isIn(DamageTypeTags.BURNS_ARMOR_STANDS) && this.getHealth() > 0.5F) {
                this.updateHealth(serverWorld, source, 4.0F);
                return false;
            } else {
                boolean bl = source.isIn(DamageTypeTags.CAN_BREAK_ARMOR_STAND);
                boolean bl2 = source.isIn(DamageTypeTags.ALWAYS_KILLS_ARMOR_STANDS);
                if (!bl && !bl2) {
                    return false;
                } else if (source.getAttacker() instanceof PlayerEntity playerEntity && !playerEntity.getAbilities().allowModifyWorld) {
                    return false;
                } else if (source.isSourceCreativePlayer()) {
                    this.playBreakSound();
                    this.spawnBreakParticles();
                    this.kill();
                    return true;
                } else {
                    long l = serverWorld.getTime();
                    if (l - this.lastHitTime > 5L && !bl2) {
                        serverWorld.sendEntityStatus(this, EntityStatuses.HIT_ARMOR_STAND);
                        this.emitGameEvent(GameEvent.ENTITY_DAMAGE, source.getAttacker());
                        this.lastHitTime = l;
                    } else {
                        this.breakAndDropItem(serverWorld, source);
                        this.spawnBreakParticles();
                        this.kill();
                    }

                    return true;
                }
            }
        } else {
            return false;
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.HIT_ARMOR_STAND) {
            if (this.getWorld().isClient) {
                this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ARMOR_STAND_HIT, this.getSoundCategory(), 0.3F, 1.0F, false);
                this.lastHitTime = this.getWorld().getTime();
            }
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    public boolean shouldRender(double distance) {
        double d = this.getBoundingBox().getAverageSideLength() * 4.0;
        if (Double.isNaN(d) || d == 0.0) {
            d = 4.0;
        }

        d *= 64.0;
        return distance < d * d;
    }

    private void spawnBreakParticles() {
        if (this.getWorld() instanceof ServerWorld) {
            ((ServerWorld)this.getWorld())
                    .spawnParticles(
                            new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.getDefaultState()),
                            this.getX(),
                            this.getBodyY(0.6666666666666666),
                            this.getZ(),
                            10,
                            this.getWidth() / 4.0F,
                            this.getHeight() / 4.0F,
                            this.getWidth() / 4.0F,
                            0.05
                    );
        }
    }

    private void updateHealth(ServerWorld world, DamageSource damageSource, float amount) {
        float f = this.getHealth();
        f -= amount;
        if (f <= 0.5F) {
            this.onBreak(world, damageSource);
            this.kill();
        } else {
            this.setHealth(f);
            this.emitGameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getAttacker());
        }
    }

    private void breakAndDropItem(ServerWorld world, DamageSource damageSource) {
        ItemStack itemStack = new ItemStack(ModItems.STATUE);
        itemStack.set(DataComponentTypes.CUSTOM_NAME, this.getCustomName());
        Block.dropStack(this.getWorld(), this.getBlockPos(), itemStack);
        this.onBreak(world, damageSource);
    }

    private void onBreak(ServerWorld world, DamageSource damageSource) {
        this.playBreakSound();
        this.drop(world, damageSource);

        for (int i = 0; i < this.heldItems.size(); i++) {
            ItemStack itemStack = this.heldItems.get(i);
            if (!itemStack.isEmpty()) {
                Block.dropStack(this.getWorld(), this.getBlockPos().up(), itemStack);
                this.heldItems.set(i, ItemStack.EMPTY);
            }
        }

        for (int ix = 0; ix < this.armorItems.size(); ix++) {
            ItemStack itemStack = this.armorItems.get(ix);
            if (!itemStack.isEmpty()) {
                Block.dropStack(this.getWorld(), this.getBlockPos().up(), itemStack);
                this.armorItems.set(ix, ItemStack.EMPTY);
            }
        }
    }

    private void playBreakSound() {
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ARMOR_STAND_BREAK, this.getSoundCategory(), 1.0F, 1.0F);
    }

    @Override
    protected float turnHead(float bodyRotation, float headRotation) {
        this.prevBodyYaw = this.prevYaw;
        this.bodyYaw = this.getYaw();
        return 0.0F;
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canClip()) {
            super.travel(movementInput);
        }
    }

    @Override
    public void setBodyYaw(float bodyYaw) {
        this.prevBodyYaw = this.prevYaw = bodyYaw;
        this.prevHeadYaw = this.headYaw = bodyYaw;
    }

    @Override
    public void setHeadYaw(float headYaw) {
        this.prevBodyYaw = this.prevYaw = headYaw;
        this.prevHeadYaw = this.headYaw = headYaw;
    }

    @Override
    public void tick() {
        super.tick();
        EulerAngle eulerAngle = this.dataTracker.get(TRACKER_HEAD_ROTATION);
        if (!this.headRotation.equals(eulerAngle)) {
            this.setHeadRotation(eulerAngle);
        }

        EulerAngle eulerAngle2 = this.dataTracker.get(TRACKER_BODY_ROTATION);
        if (!this.bodyRotation.equals(eulerAngle2)) {
            this.setBodyRotation(eulerAngle2);
        }

        EulerAngle eulerAngle3 = this.dataTracker.get(TRACKER_LEFT_ARM_ROTATION);
        if (!this.leftArmRotation.equals(eulerAngle3)) {
            this.setLeftArmRotation(eulerAngle3);
        }

        EulerAngle eulerAngle4 = this.dataTracker.get(TRACKER_RIGHT_ARM_ROTATION);
        if (!this.rightArmRotation.equals(eulerAngle4)) {
            this.setRightArmRotation(eulerAngle4);
        }
    }

    @Override
    protected void updatePotionVisibility() {
        this.setInvisible(this.invisible);
    }

    @Override
    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
        super.setInvisible(invisible);
    }

    @Override
    public boolean isBaby() {
        return this.isSmall();
    }

    @Override
    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
        this.emitGameEvent(GameEvent.ENTITY_DIE);
    }

    @Override
    public boolean isImmuneToExplosion(Explosion explosion) {
        return this.isInvisible();
    }

    @Override
    public PistonBehavior getPistonBehavior() {
        return this.isMarker() ? PistonBehavior.DESTROY : super.getPistonBehavior();
    }

    @Override
    public boolean canAvoidTraps() {
        return this.isMarker();
    }

    private void setSmall(boolean small) {
        this.dataTracker.set(ARMOR_STAND_FLAGS, this.setBitField(this.dataTracker.get(ARMOR_STAND_FLAGS), SMALL_FLAG, small));
    }

    public boolean isSmall() {
        return false;
    }

    public void setShowArms(boolean showArms) {
        this.dataTracker.set(ARMOR_STAND_FLAGS, this.setBitField(this.dataTracker.get(ARMOR_STAND_FLAGS), SHOW_ARMS_FLAG, showArms));
    }

    public boolean shouldShowArms() {
        return true;
    }

    public void setHideBasePlate(boolean hideBasePlate) {
        this.dataTracker.set(ARMOR_STAND_FLAGS, this.setBitField(this.dataTracker.get(ARMOR_STAND_FLAGS), HIDE_BASE_PLATE_FLAG, hideBasePlate));
    }

    public boolean shouldHideBasePlate() {
        return false;
    }

    private void setMarker(boolean marker) {
        this.dataTracker.set(ARMOR_STAND_FLAGS, this.setBitField(this.dataTracker.get(ARMOR_STAND_FLAGS), MARKER_FLAG, marker));
    }

    public boolean isMarker() {
        return (this.dataTracker.get(ARMOR_STAND_FLAGS) & 16) != 0;
    }

    private byte setBitField(byte value, int bitField, boolean set) {
        if (set) {
            value = (byte)(value | bitField);
        } else {
            value = (byte)(value & ~bitField);
        }

        return value;
    }

    public void setHeadRotation(EulerAngle angle) {
        this.headRotation = angle;
        this.dataTracker.set(TRACKER_HEAD_ROTATION, angle);
    }

    public void setBodyRotation(EulerAngle angle) {
        this.bodyRotation = angle;
        this.dataTracker.set(TRACKER_BODY_ROTATION, angle);
    }

    public void setLeftArmRotation(EulerAngle angle) {
        this.leftArmRotation = angle;
        this.dataTracker.set(TRACKER_LEFT_ARM_ROTATION, angle);
    }

    public void setRightArmRotation(EulerAngle angle) {
        this.rightArmRotation = angle;
        this.dataTracker.set(TRACKER_RIGHT_ARM_ROTATION, angle);
    }

    public EulerAngle getHeadRotation() {
        return this.headRotation;
    }

    public EulerAngle getBodyRotation() {
        return this.bodyRotation;
    }

    public EulerAngle getLeftArmRotation() {
        return this.leftArmRotation;
    }

    public EulerAngle getRightArmRotation() {
        return this.rightArmRotation;
    }

    @Override
    public boolean canHit() {
        return super.canHit() && !this.isMarker();
    }

    @Override
    public boolean handleAttack(Entity attacker) {
        return attacker instanceof PlayerEntity && !this.getWorld().canPlayerModifyAt((PlayerEntity)attacker, this.getBlockPos());
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    @Override
    public LivingEntity.FallSounds getFallSounds() {
        return new LivingEntity.FallSounds(SoundEvents.ENTITY_ARMOR_STAND_FALL, SoundEvents.ENTITY_ARMOR_STAND_FALL);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ARMOR_STAND_HIT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ARMOR_STAND_BREAK;
    }

    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
    }

    @Override
    public boolean isAffectedBySplashPotions() {
        return false;
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (ARMOR_STAND_FLAGS.equals(data)) {
            this.calculateDimensions();
            this.intersectionChecked = !this.isMarker();
        }

        super.onTrackedDataSet(data);
    }

    @Override
    public boolean isMobOrPlayer() {
        return false;
    }

    @Override
    public EntityDimensions getBaseDimensions(EntityPose pose) {
        return this.getDimensions(this.isMarker());
    }

    private EntityDimensions getDimensions(boolean marker) {
        if (marker) {
            return MARKER_DIMENSIONS;
        } else {
            return this.isBaby() ? SMALL_DIMENSIONS : this.getType().getDimensions();
        }
    }

    @Override
    public Vec3d getClientCameraPosVec(float tickDelta) {
        if (this.isMarker()) {
            Box box = this.getDimensions(false).getBoxAt(this.getPos());
            BlockPos blockPos = this.getBlockPos();
            int i = Integer.MIN_VALUE;

            for (BlockPos blockPos2 : BlockPos.iterate(BlockPos.ofFloored(box.minX, box.minY, box.minZ), BlockPos.ofFloored(box.maxX, box.maxY, box.maxZ))) {
                int j = Math.max(this.getWorld().getLightLevel(LightType.BLOCK, blockPos2), this.getWorld().getLightLevel(LightType.SKY, blockPos2));
                if (j == 15) {
                    return Vec3d.ofCenter(blockPos2);
                }

                if (j > i) {
                    i = j;
                    blockPos = blockPos2.toImmutable();
                }
            }

            return Vec3d.ofCenter(blockPos);
        } else {
            return super.getClientCameraPosVec(tickDelta);
        }
    }

    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(ModItems.STATUE);
    }

    @Override
    public boolean isPartOfGame() {
        return !this.isInvisible() && !this.isMarker();
    }
}
