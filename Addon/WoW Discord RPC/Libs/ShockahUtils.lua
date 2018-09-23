local MAJOR, MINOR = "ShockahUtils", 2
local Self = LibStub:NewLibrary(MAJOR, MINOR)

if not Self then
	return
end

------------------------------
-- debug
------------------------------

function Self:Dump(prefix, message)
	if message == nil then
		print(prefix..": nil")
	elseif type(message) == "table" then
		print(prefix..":")
		self:DumpTable(message, 1)
	else
		print(prefix..": "..tostring(message))
	end
end

local function PrivateDumpTable(tbl, indent, printed)
	indent = indent or 0
	for k, v in pairs(tbl) do
		formatting = string.rep("  ", indent)..k..": "
		if type(v) == "table" then
			if Self:Contains(printed, v) then
				print(formatting.."<circular reference>")
			else
				table.insert(printed, v)
				print(formatting)
				PrivateDumpTable(v, indent + 1, printed)
			end
		elseif type(v) == "boolean" then
			print(formatting..tostring(v))
		elseif type(v) == "function" then
			print(formatting.."function")
		elseif type(v) == "userdata" then
			print(formatting.."userdata")
		else
			print(formatting..v)
		end
	end
end

function Self:DumpTable(tbl, indent)
	PrivateDumpTable(tbl, indent, {})
end

------------------------------
-- numbers
------------------------------

function Self:Round(n, mult)
    mult = mult or 1
    return math.floor((n + mult / 2) / mult) * mult
end

------------------------------
-- strings
------------------------------

function Self:IsBlankString(str)
	return str == nil or str == ""
end

function Self:StringOrNil(str)
	if str == "" then
		return nil
	else
		return str
	end
end

function Self:StringStartsWith(str, prefix)
	return string.sub(str, 1, string.len(prefix)) == prefix
end

function Self:StringEndsWith(str, suffix)
	return suffix == "" or string.sub(str, -string.len(suffix)) == suffix
end

function Self:Split(str, sep)
	local fields = {}
	local pattern = string.format("([^%s]+)", sep)
	str:gsub(pattern, function(c)
		fields[#fields + 1] = c
	end)
	return fields
end

------------------------------
-- tables
------------------------------

function Self:DeepClone(v)
	if v == nil then
		return nil
	elseif type(v) == "table" then
		local new = {}
		for key, value in pairs(v) do
			new[key] = self:DeepClone(value)
		end
		return new
	else
		return v
	end
end

function Self:Clone(v)
	if v == nil then
		return nil
	elseif type(v) == "table" then
		return self:CloneInto(v, {})
	else
		return v
	end
end

function Self:CloneInto(prototype, tbl)
	for k, v in pairs(prototype) do
		tbl[k] = v
	end
	return tbl
end

function Self:Clear(tbl)
	for k in pairs(tbl) do
		tbl[k] = nil
	end
end

function Self:IsEmpty(tbl)
	return next(tbl) == nil
end

function Self:Count(tbl)
	local count = 0
	for _, _ in pairs(tbl) do
		count = count + 1
	end
	return count
end

function Self:KeyOf(tbl, value)
	for k, v in pairs(tbl) do
		if v == value then
			return k
		end
	end
	return nil
end

function Self:RemoveValue(tbl, value)
	local key = self:KeyOf(tbl, value)
	if key then
		table.remove(tbl, key)
	end
end

function Self:Contains(tbl, value)
	return self:KeyOf(tbl, value) ~= nil
end

function Self:Slice(tbl, firstIndex, length, preserveIndexes)
	preserveIndexes = preserveIndexes or false
	local result = {}
	local lastIndex = firstIndex + length - 1

	if preserveIndexes then
		for i = firstIndex, lastIndex do
			result[i] = tbl[i]
		end
	else
		for i = firstIndex, lastIndex do
			table.insert(result, tbl[i])
		end
	end

	return result
end

function Self:Keys(tbl)
	local result = {}
	for k, _ in pairs(tbl) do
		table.insert(result, k)
	end
	return result
end

function Self:Values(tbl)
	local result = {}
	for _, v in pairs(tbl) do
		table.insert(result, v)
	end
	return result
end

function Self:Filter(tbl, filterFunction)
	local result = {}
	for _, v in pairs(tbl) do
		if filterFunction(v) then
			table.insert(result, v)
		end
	end
	return result
end

function Self:FilterFirst(tbl, filterFunction)
	for _, v in pairs(tbl) do
		if filterFunction(v) then
			return v
		end
	end
	return nil
end

function Self:Map(tbl, mapFunction)
	local result = {}
	for _, v in pairs(tbl) do
		table.insert(result, mapFunction(v))
	end
	return result
end

function Self:FlatMap(tbl, mapFunction)
	local result = {}
	for _, v in pairs(tbl) do
		local mapped = mapFunction(v)
		for _, v2 in pairs(mapped) do
			table.insert(result, v2)
		end
	end
	return result
end

function Self:FilterContains(tbl, filterFunction)
	for _, v in pairs(tbl) do
		if filterFunction(v) then
			return true
		end
	end
	return false
end

function Self:FilterRemove(tbl, filterFunction)
	for k, v in pairs(tbl) do
		if filterFunction(v) then
			tbl[k] = nil
		end
	end
end

function Self:Group(tbl, groupingFunction)
	local result = {}
	for _, v in pairs(tbl) do
		local group = groupingFunction(v)
		if not result[group] then
			result[group] = {}
		end
		table.insert(result[group], v)
	end
	return result
end

function Self:Join(delimiter, tbl)
	local result = ""
	for i, v in ipairs(tbl) do
		if i ~= 1 then
			result = result..delimiter
		end
		result = result..v
	end
	return result
end

function Self:InsertAll(tbl, values)
	for _, value in pairs(values) do
		table.insert(tbl, value)
	end
end

function Self:InsertUnique(tbl, value)
	for _, v in pairs(tbl) do
		if v == value then
			return
		end
	end
	table.insert(tbl, value)
end

function Self:InsertAllUnique(tbl, values)
	for _, value in pairs(values) do
		self:InsertUnique(tbl, value)
	end
end

function Self:Without(tbl, values)
	local result = self:Clone(tbl)
	for _, value in pairs(values) do
		self:RemoveValue(result, value)
	end
	return result
end

------------------------------
-- items
------------------------------

function Self:ParseItemLink(link)
	local linkParts = { string.find(link, "|?c?f?f?(%x*)|?H?([^:]*):?(%d+):?(%d*):?(%d*):?(%d*):?(%d*):?(%d*):?(%-?%d*):?(%-?%d*):?(%d*):?(%d*)|?h?%[?([^%[%]]*)%]?|?h?|?r?") }
	return {
		itemString = string.match(link, "item[%-?%d:]+"),
		color = linkParts[3],
		linkType = linkParts[4],
		ID = linkParts[5],
		enchant = linkParts[6],
		gems = {
			linkParts[7],
			linkParts[8],
			linkParts[9],
			linkParts[10],
		},
		suffix = linkParts[11],
		unique = linkParts[12],
		linkLevel = linkParts[13],
		reforging = linkParts[14],
		name = linkParts[15],
	}
end

------------------------------
-- players
------------------------------

function Self:GetPlayerNameWithRealm(player)
	player = player or GetUnitName("player", true)
	return string.find(player, "-") and player or player.."-"..GetRealmName()
end

function Self:GetPlayerNameWithOptionalRealm(player)
	player = player or GetUnitName("player", true)
	return string.gsub(player, "%-"..GetRealmName(), "")
end

------------------------------
-- chatbox
------------------------------

function Self:FindActiveChatEditbox()
	for i = 1, 10 do
		local frame = _G["ChatFrame"..i.."EditBox"]
		if frame and frame:IsVisible() then
			return frame
		end
	end
	return nil
end

function Self:InsertInChatEditbox(text)
	local chatEditbox = self:FindActiveChatEditbox()
	if chatEditbox then
		chatEditbox:Insert(text)
	end
end

------------------------------
-- interpolation
------------------------------

function Self:Lerp(f, a, b)
	if type(a) == "table" and type(b) == "table" then
		local result = {}
		for i = 1, #a do
			result[i] = self:LerpValue(f, a[i], b[i])
		end
		return result
	else
		return self:LerpValue(f, a, b)
	end
end

function Self:LerpValue(f, a, b)
	return a + (b - a) * f
end

------------------------------
-- tooltip
------------------------------

local parseableTooltip = nil

function Self:ParseTooltip(setupCallback, parseCallback)
	if not parseableTooltip then
		parseableTooltip = CreateFrame("GameTooltip", "ShockahUtilsParseTooltip", UIParent, "GameTooltipTemplate")
	end

	parseableTooltip:SetOwner(UIParent, "ANCHOR_NONE")
	parseableTooltip:ClearLines()
	setupCallback(parseableTooltip)

	local result = {
		left = {},
		right = {},
	}

	-- TODO: parseableTooltip:NumLines()

	local continue = true
	local index = 0
	while continue do
		index = index + 1
		local leftLabel = _G[parseableTooltip:GetName().."TextLeft"..index]
		local rightLabel = _G[parseableTooltip:GetName().."TextRight"..index]

		if (not leftLabel) and (not rightLabel) then
			continue = false
		else
			local r, g, b

			if leftLabel then
				r, g, b = leftLabel:GetTextColor()
			else
				r, g, b = 1.0, 1.0, 1.0
			end
			table.insert(result.left, {
				text = leftLabel and leftLabel:GetText() or nil,
				r = r,
				g = g,
				b = b,
			})

			if rightLabel then
				r, g, b = rightLabel:GetTextColor()
			else
				r, g, b = 1.0, 1.0, 1.0
			end
			table.insert(result.right, {
				text = rightLabel and rightLabel:GetText() or nil,
				r = r,
				g = g,
				b = b,
			})
		end
	end

	continue = true
	while continue do
		local leftObj = result.left[index] or {}
		local rightObj = result.right[index] or {}

		if self:IsBlankString(leftObj.text) and self:IsBlankString(rightObj.text) then
			table.remove(result.left, index)
			table.remove(result.right, index)
		else
			continue = false
		end
		index = index - 1
	end

	local parseResult = { parseCallback(result.left, result.right) }
	parseableTooltip:Hide()
	return unpack(parseResult)
end