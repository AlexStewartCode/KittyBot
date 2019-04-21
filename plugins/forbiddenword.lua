local time = os.time();

function plugin(message) 
	if(string.match(message, "purple"))then
		local timedif = os.time() - time;
		time = os.time();
		days = (timedif / (60*60*24));
		hours = ((timedif / (60*60)) % 24); 
		mins = ((timedif / (60)) % 60); 
		secs = timedif % 60;
		return round(days) .. " " .. round(hours) .. " " .. round(mins) .. " " .. round(secs); 
	end
	
	return nil;
end

function round(num) 
	return math.floor(num + 0.5)
end