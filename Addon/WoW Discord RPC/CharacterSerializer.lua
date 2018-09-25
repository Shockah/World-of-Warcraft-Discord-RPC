local addonName, addonTable = ...
local Addon = _G[addonName]
local S = LibStub:GetLibrary("ShockahUtils")

local Class = {}
Addon.CharacterSerializer = Class

local factionsEnum = {
	"Neutral", "Alliance", "Horde"
}

local specializationsEnum = {
	WARRIOR = {
		Arms = 1,
		Fury = 2,
		Protection = 3,
	},
	DEATHKNIGHT = {
		Blood = 4,
		Frost = 5,
		Unholy = 6,
	},
	PALADIN = {
		Holy = 7,
		Protection = 8,
		Retribution = 9,
	},
	MONK = {
		Brewmaster = 10,
		Mistweaver = 11,
		Windwalker = 12,
	},
	PRIEST = {
		Discipline = 13,
		Holy = 14,
		Shadow = 15,
	},
	SHAMAN = {
		Elemental = 16,
		Enhancement = 17,
		Restoration = 18,
	},
	DRUID = {
		Balance = 19,
		Feral = 20,
		Guardian = 21,
		Restoration = 22,
	},
	ROGUE = {
		Assassination = 23,
		Outlaw = 24,
		Subtlety = 25,
	},
	MAGE = {
		Arcane = 26,
		Fire = 27,
		Frost = 28,
	},
	WARLOCK = {
		Affliction = 29,
		Demonology = 30,
		Destruction = 31,
	},
	HUNTER = {
		["Beast Mastery"] = 32,
		Marksmanship = 33,
		Survival = 34,
	},
	DEMONHUNTER = {
		Havoc = 35,
		Vengeance = 36,
	},
}

function Class:Serialize(bits)
	if self:IncludeName() then
		bits:Write(true)
		bits:WriteString(UnitName("player"))
	else
		bits:Write(false)
	end

	if self:IncludeRealm() then
		bits:Write(true)
		bits:WriteString(GetRealmName())
	else
		bits:Write(false)
	end

	if self:IncludeRegion() then
		bits:Write(true)
		bits:WriteUInt(3, GetCurrentRegion() - 1)
	else
		bits:Write(false)
	end

	if self:IncludeFaction() then
		bits:Write(true)
		bits:WriteUInt(2, S:KeyOf(factionsEnum, UnitFactionGroup("player")) - 1)
	else
		bits:Write(false)
	end

	if self:IncludeSpecialization() then
		bits:Write(true)
		local classKey = select(2, UnitClass("player"))
		local specializationKey = GetSpecialization()
		bits:WriteUInt(6, specializationsEnum[classKey][specializationKey] - 1)
	else
		bits:Write(false)
	end

	if self:IncludeLevel() then
		bits:Write(true)
		bits:WriteUInt(8, UnitLevel("player"))
	else
		bits:Write(false)
	end
end

function Class:IncludeName()
	return true
end

function Class:IncludeRealm()
	return true
end

function Class:IncludeRegion()
	return true
end

function Class:IncludeFaction()
	return true
end

function Class:IncludeSpecialization()
	return true
end

function Class:IncludeLevel()
	return true
end