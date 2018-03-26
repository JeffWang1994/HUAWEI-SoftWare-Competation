function J = ComputeCost(X,y,w)
    % Prepare Variables
    m = length(y);
    
    % Calculate Hypothesis
    h = X * w;
    
    % Calculate Cost
    J = 1 / (2 * m) * sum((h - y) .^ 2);
end