package com.degraffa.mcdnd.character;

public class CharacterStats {
    private int hitPoints;
    private int hitPointMax;
    private int tempHitPoints;

    private int armorClass;
    private int speed;
    private int flySpeed;
    private int swimSpeed;

    private int strengthScore;
    private int strengthMod;
    private int dexScore;
    private int dexMod;
    private int conScore;
    private int conMod;
    private int intScore;
    private int intMod;
    private int wisScore;
    private int wisMod;
    private int charismaScore;
    private int charismaMod;

    private int strengthSave;
    private int dexSave;
    private int conSave;
    private int intSave;
    private int wisSave;
    private int charismaSave;

    private int skillAcrobatics;
    private int skillAnimalHandling;
    private int skillArcana;
    private int skillAthletics;
    private int skillDeception;
    private int skillHistory;
    private int skillInsight;
    private int skillIntimidation;
    private int skillInvestigation;
    private int skillMedicine;
    private int skillNature;
    private int skillPerception;
    private int skillPerformance;
    private int skillPersuasion;
    private int skillReligion;
    private int skillSleightOfHand;
    private int skillStealth;
    private int skillSurvival;

    private int passivePerception;

    public CharacterStats(int maxHP, int AC, int speed, int flySpeed, int swimSpeed, int str, int dex, int con, int intel, int wis, int cha) {
        this.hitPointMax = maxHP;
        this.hitPoints = maxHP;

        this.armorClass = AC;

        this.speed = speed;
        this.flySpeed = flySpeed;
        this.swimSpeed = swimSpeed;

        this.strengthScore = str;
        this.strengthMod = abilityScoreToMod(str);
        this.dexScore = dex;
        this.dexMod = abilityScoreToMod(str);
        this.conScore = con;
        this.conMod = abilityScoreToMod(con);
        this.intScore = intel;
        this.intScore = abilityScoreToMod(intel);
        this.wisScore = wis;
        this.wisScore = abilityScoreToMod(wis);
        this.charismaScore = cha;
        this.charismaMod = abilityScoreToMod(cha);
    }

    private int abilityScoreToMod(int score) {
        return (score - 10) / 2;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getHitPointMax() {
        return hitPointMax;
    }

    public void setHitPointMax(int hitPointMax) {
        this.hitPointMax = hitPointMax;
    }

    public int getTempHitPoints() {
        return tempHitPoints;
    }

    public void setTempHitPoints(int tempHitPoints) {
        this.tempHitPoints = tempHitPoints;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getFlySpeed() {
        return flySpeed;
    }

    public void setFlySpeed(int flySpeed) {
        this.flySpeed = flySpeed;
    }

    public int getSwimSpeed() {
        return swimSpeed;
    }

    public void setSwimSpeed(int swimSpeed) {
        this.swimSpeed = swimSpeed;
    }

    public int getStrengthScore() {
        return strengthScore;
    }

    public void setStrengthScore(int strengthScore) {
        this.strengthScore = strengthScore;
    }

    public int getStrengthMod() {
        return strengthMod;
    }

    public void setStrengthMod(int strengthMod) {
        this.strengthMod = strengthMod;
    }

    public int getCharismaScore() {
        return charismaScore;
    }

    public void setCharismaScore(int charismaScore) {
        this.charismaScore = charismaScore;
    }

    public int getCharismaMod() {
        return charismaMod;
    }

    public void setCharismaMod(int charismaMod) {
        this.charismaMod = charismaMod;
    }

    public int getWisMod() {
        return wisMod;
    }

    public void setWisMod(int wisMod) {
        this.wisMod = wisMod;
    }

    public int getWisScore() {
        return wisScore;
    }

    public void setWisScore(int wisScore) {
        this.wisScore = wisScore;
    }

    public int getIntMod() {
        return intMod;
    }

    public void setIntMod(int intMod) {
        this.intMod = intMod;
    }

    public int getIntScore() {
        return intScore;
    }

    public void setIntScore(int intScore) {
        this.intScore = intScore;
    }

    public int getConMod() {
        return conMod;
    }

    public void setConMod(int conMod) {
        this.conMod = conMod;
    }

    public int getConScore() {
        return conScore;
    }

    public void setConScore(int conScore) {
        this.conScore = conScore;
    }

    public int getDexMod() {
        return dexMod;
    }

    public void setDexMod(int dexMod) {
        this.dexMod = dexMod;
    }

    public int getDexScore() {
        return dexScore;
    }

    public void setDexScore(int dexScore) {
        this.dexScore = dexScore;
    }

    public int getStrengthSave() {
        return strengthSave;
    }

    public void setStrengthSave(int strengthSave) {
        this.strengthSave = strengthSave;
    }

    public int getDexSave() {
        return dexSave;
    }

    public void setDexSave(int dexSave) {
        this.dexSave = dexSave;
    }

    public int getConSave() {
        return conSave;
    }

    public void setConSave(int conSave) {
        this.conSave = conSave;
    }

    public int getIntSave() {
        return intSave;
    }

    public void setIntSave(int intSave) {
        this.intSave = intSave;
    }

    public int getWisSave() {
        return wisSave;
    }

    public void setWisSave(int wisSave) {
        this.wisSave = wisSave;
    }

    public int getCharismaSave() {
        return charismaSave;
    }

    public void setCharismaSave(int charismaSave) {
        this.charismaSave = charismaSave;
    }

    public int getSkillAcrobatics() {
        return skillAcrobatics;
    }

    public void setSkillAcrobatics(int skillAcrobatics) {
        this.skillAcrobatics = skillAcrobatics;
    }

    public int getSkillAnimalHandling() {
        return skillAnimalHandling;
    }

    public void setSkillAnimalHandling(int skillAnimalHandling) {
        this.skillAnimalHandling = skillAnimalHandling;
    }

    public int getSkillArcana() {
        return skillArcana;
    }

    public void setSkillArcana(int skillArcana) {
        this.skillArcana = skillArcana;
    }

    public int getSkillAthletics() {
        return skillAthletics;
    }

    public void setSkillAthletics(int skillAthletics) {
        this.skillAthletics = skillAthletics;
    }

    public int getSkillDeception() {
        return skillDeception;
    }

    public void setSkillDeception(int skillDeception) {
        this.skillDeception = skillDeception;
    }

    public int getSkillHistory() {
        return skillHistory;
    }

    public void setSkillHistory(int skillHistory) {
        this.skillHistory = skillHistory;
    }

    public int getSkillInsight() {
        return skillInsight;
    }

    public void setSkillInsight(int skillInsight) {
        this.skillInsight = skillInsight;
    }

    public int getSkillIntimidation() {
        return skillIntimidation;
    }

    public void setSkillIntimidation(int skillIntimidation) {
        this.skillIntimidation = skillIntimidation;
    }

    public int getSkillInvestigation() {
        return skillInvestigation;
    }

    public void setSkillInvestigation(int skillInvestigation) {
        this.skillInvestigation = skillInvestigation;
    }

    public int getSkillMedicine() {
        return skillMedicine;
    }

    public void setSkillMedicine(int skillMedicine) {
        this.skillMedicine = skillMedicine;
    }

    public int getSkillNature() {
        return skillNature;
    }

    public void setSkillNature(int skillNature) {
        this.skillNature = skillNature;
    }

    public int getSkillPerception() {
        return skillPerception;
    }

    public void setSkillPerception(int skillPerception) {
        this.skillPerception = skillPerception;
    }

    public int getSkillPerformance() {
        return skillPerformance;
    }

    public void setSkillPerformance(int skillPerformance) {
        this.skillPerformance = skillPerformance;
    }

    public int getSkillPersuasion() {
        return skillPersuasion;
    }

    public void setSkillPersuasion(int skillPersuasion) {
        this.skillPersuasion = skillPersuasion;
    }

    public int getSkillReligion() {
        return skillReligion;
    }

    public void setSkillReligion(int skillReligion) {
        this.skillReligion = skillReligion;
    }

    public int getSkillSleightOfHand() {
        return skillSleightOfHand;
    }

    public void setSkillSleightOfHand(int skillSleightOfHand) {
        this.skillSleightOfHand = skillSleightOfHand;
    }

    public int getSkillStealth() {
        return skillStealth;
    }

    public void setSkillStealth(int skillStealth) {
        this.skillStealth = skillStealth;
    }

    public int getSkillSurvival() {
        return skillSurvival;
    }

    public void setSkillSurvival(int skillSurvival) {
        this.skillSurvival = skillSurvival;
    }

    public int getPassivePerception() {
        return passivePerception;
    }

    public void setPassivePerception(int passivePerception) {
        this.passivePerception = passivePerception;
    }
}
